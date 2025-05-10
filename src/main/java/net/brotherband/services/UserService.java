package net.brotherband.services;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.brotherband.DTOs.UserDTO;
import net.brotherband.DTOs.UserRegisterDTO;
import net.brotherband.exceptions.ResourceNotFoundException;
import net.brotherband.exceptions.UsernameAlreadyExistsException;
import net.brotherband.models.User;
import net.brotherband.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserDTO register(UserRegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        
        // Create and save the new user
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .registeredIn(LocalDateTime.now())
                .build();
        
        User savedUser = userRepository.save(user);
        
        return mapToUserDTO(savedUser);
    }
    
    public UserDTO findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        return mapToUserDTO(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .registeredIn(user.getRegisteredIn())
                .build();
    }
}