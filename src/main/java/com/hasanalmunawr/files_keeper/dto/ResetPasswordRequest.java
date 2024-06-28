package com.hasanalmunawr.files_keeper.dto;

public record ResetPasswordRequest(
        String email,
        String oldPassword,
        String newPassword,
        String passwordConfirm
) {
}
