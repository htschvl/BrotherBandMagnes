package net.brotherband.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import net.brotherband.DTOs.LoginRequestDTO;
import net.brotherband.DTOs.LoginResponseDTO;
import net.brotherband.DTOs.UserDTO;
import net.brotherband.DTOs.UserRegisterDTO;
import net.brotherband.exceptions.AuthenticationFailedException;
import net.brotherband.security.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String username = authentication.getName();
            String token = jwtUtil.generateToken(username);

            return LoginResponseDTO.builder()
                    .message("Login successful")
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }

    public UserDTO register(UserRegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }
}