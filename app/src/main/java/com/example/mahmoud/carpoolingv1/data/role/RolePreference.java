package com.example.mahmoud.carpoolingv1.data.role;

public interface RolePreference {
    String ROLE_DRIVER = "driver";

    void putCurrentRole(String value);

    String getCurrentRole();

    void putCurrentTag(String value);

    String getCurrentTag();
}
