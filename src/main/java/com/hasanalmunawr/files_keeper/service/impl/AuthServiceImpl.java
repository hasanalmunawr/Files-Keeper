package com.hasanalmunawr.files_keeper.service.impl;

import com.hasanalmunawr.files_keeper.code.CodeEntity;
import com.hasanalmunawr.files_keeper.code.CodeRepository;
import com.hasanalmunawr.files_keeper.dto.LoginRequest;
import com.hasanalmunawr.files_keeper.dto.RegisterRequest;
import com.hasanalmunawr.files_keeper.dto.ResetPasswordRequest;
import com.hasanalmunawr.files_keeper.dto.response.LoginResponse;
import com.hasanalmunawr.files_keeper.email.EmailService;
import com.hasanalmunawr.files_keeper.email.EmailTemplateName;
import com.hasanalmunawr.files_keeper.exception.CodeInvalidException;
import com.hasanalmunawr.files_keeper.exception.EmailNotFoundException;
import com.hasanalmunawr.files_keeper.exception.UserAlreadyExistException;
import com.hasanalmunawr.files_keeper.security.JwtService;
import com.hasanalmunawr.files_keeper.service.AuthService;
import com.hasanalmunawr.files_keeper.token.TokenEntity;
import com.hasanalmunawr.files_keeper.token.TokenRepository;
import com.hasanalmunawr.files_keeper.token.TokenType;
import com.hasanalmunawr.files_keeper.user.Role;
import com.hasanalmunawr.files_keeper.user.UserEntity;
import com.hasanalmunawr.files_keeper.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;

import static com.hasanalmunawr.files_keeper.email.EmailTemplateName.*;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CodeRepository codeRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public void register(RegisterRequest request) {
        log.info("Register request: {}", request);
        try {
            var userEmail = userRepository.findByEmail(request.getEmail());
            if (userEmail.isPresent()) {
                throw new UserAlreadyExistException("User With " + request.getEmail() + " Already Exist");
            }

            UserEntity user = UserEntity.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .role(Role.USER)
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .enabled(false)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();

            UserEntity savedUser = userRepository.save(user);
            sendValidationEmail(savedUser, ACTIVATE_ACCOUNT);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Login request: {}", request.email());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            var userLogin = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new EmailNotFoundException("Email Not Found" + request.email()));

            String generatedToken = jwtService.generateToken(userLogin);
            long accessExpiration = jwtService.getJwtExpiration();

            savedUserToken(generatedToken, userLogin);
            return LoginResponse.builder()
                    .accessTokenExpiry((int) accessExpiration)
                    .accessToken(generatedToken)
                    .build();
        } catch (Exception e) {
            log.error("Authentication failed for email: {}", request.email(), e);
            throw new EntityNotFoundException("Authentication failed for email: " + request.email());
        }

    }


    @Transactional
    @Override
    public void activateAccount(Integer code) {
        CodeEntity codeEntity = codeRepository.findByTokenCode(code)
                .orElseThrow(CodeInvalidException::new);
        codeEntity.setValidatedAt(now());
        log.info("AuthService: Activate account with code: {}", codeEntity);
        if (now().isAfter(codeEntity.getExpiresAt())) {
            throw new CodeInvalidException("Activation token has expired");
        }

        var userEntity = userRepository.findById(codeEntity.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        log.info("AuthService: Code has Activated : {}", code);
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        sendValidationEmail(user, RESET_PASSWORD); // Include create a code and send the message email
    }

    @Override
    @Transactional
    public void validatePasswordCode(Integer tokenCode, String newPassword) {
        CodeEntity codeEntity = codeRepository.findByTokenCode(tokenCode)
                .orElseThrow(CodeInvalidException::new);
        codeEntity.setValidatedAt(now());

        if (now().isAfter(codeEntity.getExpiresAt())) {
            throw new CodeInvalidException("Activation token has expired");
        }
        try {
            var userEntity = userRepository.findById(codeEntity.getUser().getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

            userEntity.setPassword(newPassword);
            userRepository.save(userEntity);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User Not Found");
        }
    }


    @Override
    public void updatePassword(UserEntity currentUser) throws MessagingException {
        sendValidationEmail(currentUser, UPDATE_PASSWORD);
    }

    @Transactional
    @Override
    public void changePassword(
            UserEntity currentUser,
            Integer code,
            ResetPasswordRequest request) throws MessagingException {
        if (!passwordEncoder.matches(currentUser.getPassword(), request.oldPassword())) {
            throw new IllegalAccessError("Password does not match");
        }

        try {
            CodeEntity codeEntity = codeRepository.findByTokenCode(code)
                    .orElseThrow(CodeInvalidException::new);
            codeEntity.setValidatedAt(now());

            if (now().isAfter(codeEntity.getExpiresAt())) {
                throw new CodeInvalidException("Activation token has expired");
            }

            currentUser.setPassword(passwordEncoder.encode(request.newPassword()));
            userRepository.save(currentUser);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Password Not Found");
        }

    }


    private boolean isCodeValid(Integer tokenCode) {
        CodeEntity codeEntity = codeRepository.findByTokenCode(tokenCode)
                .orElseThrow(CodeInvalidException::new);
        codeEntity.setValidatedAt(now());

        if (now().isAfter(codeEntity.getExpiresAt())) {
            throw new CodeInvalidException("Activation token has expired");
        }
        return true;
    }

    private Role convertStrToRole(String role) {
        if (role.equalsIgnoreCase("admin")) {
            return Role.ADMIN;
        } else if (role.equalsIgnoreCase("user")) {
            return Role.USER;
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
    }

    private void savedUserToken(String jwtToken, UserEntity userLogin) {
        TokenEntity token = TokenEntity.builder()
                .token(jwtToken)
                .user(userLogin)
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        tokenRepository.save(token);
    }

    private void sendValidationEmail(UserEntity user, EmailTemplateName templateName) throws MessagingException {
        var newToken = generateAndSaveActivationCode(user, templateName.getName());

        emailService.sendEmailActivateAccount(
                user.getEmail(),
                user.getFullName(),
                templateName,
                String.valueOf(newToken),
                templateName.getName()
        );
    }

    private Integer generateAndSaveActivationCode(UserEntity user, String type) {
        Integer generatedCode = generateActivationCode();

        CodeEntity token = CodeEntity.builder()
                .tokenCode(generatedCode)
                .expiresAt(now().plusMinutes(10))
                .type(type)
                .user(user)
                .build();
        codeRepository.save(token);

        return generatedCode;
    }

    private Integer generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return Integer.parseInt(codeBuilder.toString());
    }

}
