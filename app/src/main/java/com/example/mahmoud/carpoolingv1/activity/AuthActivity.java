package com.example.mahmoud.carpoolingv1.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahmoud.carpoolingv1.R;
import com.example.mahmoud.carpoolingv1.mvp.presenter.AuthPresenter;
import com.example.mahmoud.carpoolingv1.mvp.view.AuthView;
import com.example.mahmoud.carpoolingv1.service.AuthUserService;
import com.example.mahmoud.carpoolingv1.service.UserService;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.OnClick;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;




public class AuthActivity extends BaseActivity
        implements OnCompleteListener<AuthResult>
{

    public static final String TAG = AuthActivity.class.getSimpleName();

    EditText editPassword;
    EditText editUsername;
    LoginButton mLoginButton;


    private AuthPresenter presenter;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editPassword=findViewById(R.id.edit_password);
        editUsername=findViewById(R.id.edit_username);
        mLoginButton=findViewById(R.id.loginButton);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        mCallbackManager = CallbackManager.Factory.create();

        if (presenter == null) {
            firebaseAuth = FirebaseAuth.getInstance();
            presenter = new AuthPresenter(new AuthUserService(firebaseAuth),new AuthView(this),new UserService());
        }

        presenter.init();
        mLoginButton.setReadPermissions("email", "public_profile");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFbAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(AuthActivity.this, R.string.loginfb_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(AuthActivity.this, R.string.loginfb_error, Toast.LENGTH_SHORT).show();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(TAG, "user:" + user);
                if (user != null) {
                    presenter.showMain();
                    removeAuthListener();
                    mLoginButton.unregisterCallback(mCallbackManager);
                }
            }
        };
    }

    private void handleFbAccessToken(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.loginfirebase_error, Toast.LENGTH_LONG).show();
                } else {
                    String facebookUserId = "";
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Log.d(TAG, "user:" + user);
                    if (user != null) {
                        presenter.createOrUpdateAuxUser(user);
                        presenter.createUserIfItDoesntExist(user);
                        presenter.showMain();
                        removeAuthListener();
                        mLoginButton.unregisterCallback(mCallbackManager);
                    } else {
                        Toast.makeText(AuthActivity.this, "فشل التوثيق",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.button_login)
    void onLoginCLick() {
        final String username = editUsername.getText().toString();
        final String password = editPassword.getText().toString();
        presenter.loginUser(username, password);
    }

    @Override
    public void onComplete(@NonNull final Task<AuthResult> task) {
        presenter.onCompleteLogin(task);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        removeAuthListener();
    }

    private void removeAuthListener() {
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
