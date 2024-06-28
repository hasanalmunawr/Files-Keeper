package com.hasanalmunawr.files_keeper.security;

import com.hasanalmunawr.files_keeper.user.UserEntity;

import java.util.Objects;

import static com.hasanalmunawr.files_keeper.user.Role.ADMIN;

public class AdminSecurity {

    public static boolean isAdmin(UserEntity user) {
        if (!Objects.equals(user.getRole(), ADMIN)) {
            return false;
        }
        return true;
    }
}
