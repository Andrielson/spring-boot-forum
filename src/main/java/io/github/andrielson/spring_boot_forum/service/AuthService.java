package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.RegisterRequest;
import io.github.andrielson.spring_boot_forum.model.NotificationEmail;
import io.github.andrielson.spring_boot_forum.model.User;
import io.github.andrielson.spring_boot_forum.model.VerificationToken;
import io.github.andrielson.spring_boot_forum.repository.UserRepository;
import io.github.andrielson.spring_boot_forum.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(
                new NotificationEmail(
                        "Please activate your account",
                        user.getEmail(),
                        "Thank you for signing up to Spring Reddit, " +
                                "please click on the below url to activate your account: " +
                                "http://localhost:8080/api/auth/accountVerification/" + token
                )
        );
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;
    }
}
