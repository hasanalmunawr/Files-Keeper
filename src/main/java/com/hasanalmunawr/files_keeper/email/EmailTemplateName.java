package com.hasanalmunawr.files_keeper.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("Reset Password"),
    UPDATE_PASSWORD("Update Password");
    ;

    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }
}