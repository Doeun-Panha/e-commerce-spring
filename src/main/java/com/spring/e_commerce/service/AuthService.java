package com.spring.e_commerce.service;

import com.spring.e_commerce.dto.AuthResponse;
import com.spring.e_commerce.dto.LoginRequest;
import com.spring.e_commerce.dto.RegisterRequest;
import com.spring.e_commerce.model.Role;
import com.spring.e_commerce.model.User;
import com.spring.e_commerce.repository.UserRepository;
import com.spring.e_commerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        // It will throw an exception if the password/username is wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // the user is authenticated!
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();

        // Generate the "Badge"
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public AuthResponse register(RegisterRequest request) {
        // 1. Create a new User object
        var user = new User();
        user.setUsername(request.username());

        // 2. ENCODE the password before saving
        user.setPassword(passwordEncoder.encode(request.password()));

        // 3. Set the role (default to USER if null)
        user.setRole(request.role() != null ? request.role() : Role.USER);

        // 4. Save to Database
        userRepository.save(user);

        // 5. Generate a token so they are "logged in" immediately after registering
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }
}
