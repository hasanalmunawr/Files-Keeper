package com.hasanalmunawr.files_keeper.authentication;

import com.hasanalmunawr.files_keeper.dto.CodeRequest;
import com.hasanalmunawr.files_keeper.dto.LoginRequest;
import com.hasanalmunawr.files_keeper.dto.RegisterRequest;
import com.hasanalmunawr.files_keeper.dto.ResetPasswordRequest;
import com.hasanalmunawr.files_keeper.dto.response.LoginResponse;
import com.hasanalmunawr.files_keeper.service.AuthService;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            path = "/register",
            consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RedirectView register(
            @RequestBody RegisterRequest registerRequest
    ) {
        authService.register(registerRequest);

        return new RedirectView("/api/v1/auth/activate-account");
    }

    @PostMapping(
            path = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest registerRequest
    ) {
        return ResponseEntity.ok(authService.login(registerRequest));
    }

    @PostMapping(path = "/activate-account")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activateAccount(
            @RequestBody CodeRequest request
    ) {
        authService.activateAccount(request.code());
    }


    @PostMapping(path = "/forgot-password")
    public RedirectView post(
            @RequestBody ResetPasswordRequest request
    ) throws MessagingException {
        authService.forgotPassword(request.email());
        return new RedirectView("/api/v1/auth/validate/password-code");
    }

    @PutMapping(path = "/validate/password-code")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void validatePasswordCode(
            @RequestBody CodeRequest request
    ) throws MessagingException {
        authService.validatePasswordCode(request.code(), request.newPassword());
    }

    @PostMapping("/send-update/password")
    public RedirectView requestUpdatePassword(
            @AuthenticationPrincipal UserEntity currentUser
    ) throws MessagingException {

        authService.updatePassword(currentUser);

        return new RedirectView("/");
    }

    @PutMapping("/update/password")
    public void updatePassword(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestParam("code") Integer code,
            @RequestBody ResetPasswordRequest request
    ) throws MessagingException {
        authService.changePassword(currentUser, code, request);
    }


}
