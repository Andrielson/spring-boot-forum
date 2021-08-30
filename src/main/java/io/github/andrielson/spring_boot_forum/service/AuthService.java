package io.github.andrielson.spring_boot_forum.service;

import io.github.andrielson.spring_boot_forum.dto.AuthenticationResponse;
import io.github.andrielson.spring_boot_forum.dto.LoginRequest;
import io.github.andrielson.spring_boot_forum.dto.RegisterRequest;
import io.github.andrielson.spring_boot_forum.exceptions.ForumException;
import io.github.andrielson.spring_boot_forum.model.NotificationEmail;
import io.github.andrielson.spring_boot_forum.model.User;
import io.github.andrielson.spring_boot_forum.model.VerificationToken;
import io.github.andrielson.spring_boot_forum.repository.UserRepository;
import io.github.andrielson.spring_boot_forum.repository.VerificationTokenRepository;
import io.github.andrielson.spring_boot_forum.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        var token = generateVerificationToken(user);
        var notificationEmail = new NotificationEmail(
                "Please activate your account",
                user.getEmail(),
                "Thank you for signing up to Spring Reddit, " +
                        "please click on the below url to activate your account: " +
                        "http://localhost:8080/api/auth/accountVerification/" + token
        );
        mailService.sendMail(notificationEmail);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        var principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    private String generateVerificationToken(User user) {
        var token = UUID.randomUUID().toString();
        var verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public void verifyAccount(String token) {
        var optionalVerificationToken = verificationTokenRepository.findByToken(token);
        var verificationToken = optionalVerificationToken.orElseThrow(() -> new ForumException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        var username = verificationToken.getUser().getUsername();
        var optionalUser = userRepository.findByUsername(username);
        var user = optionalUser.orElseThrow(() -> new ForumException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        var username = loginRequest.getUsername();
        var password = loginRequest.getPassword();
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, username);
    }
}
