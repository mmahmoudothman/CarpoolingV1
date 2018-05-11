package com.example.mahmoud.carpoolingv1.mvp.presenter.rx;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public abstract class DefaultPresenterConsumer<T, P> implements Consumer<T> {

    private final WeakReference<P> presenterWeakReference;

    public DefaultPresenterConsumer(@NonNull P presenter) {
        this.presenterWeakReference = new WeakReference<>(presenter);
    }

    public P getPresenter() {
        return presenterWeakReference.get();
    }
}
