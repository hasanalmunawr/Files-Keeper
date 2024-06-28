package com.hasanalmunawr.files_keeper.service;

import com.hasanalmunawr.files_keeper.dto.LoginRequest;
import com.hasanalmunawr.files_keeper.dto.RegisterRequest;
import com.hasanalmunawr.files_keeper.dto.ResetPasswordRequest;
import com.hasanalmunawr.files_keeper.dto.response.LoginResponse;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import jakarta.mail.MessagingException;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void activateAccount(Integer tokenCode);

    void forgotPassword(String email) throws MessagingException;

    void updatePassword(UserEntity currentUser) throws MessagingException;

    void validatePasswordCode(
            Integer tokenCode,
            String newPassword) throws MessagingException;

    void changePassword(
            UserEntity currentUser,
            Integer code,
            ResetPasswordRequest request
    ) throws MessagingException;
}
