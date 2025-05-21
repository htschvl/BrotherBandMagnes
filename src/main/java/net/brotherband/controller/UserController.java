package net.brotherband.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brotherband.DTOs.MeDTO;
import net.brotherband.DTOs.ProfileDTO;
import net.brotherband.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<MeDTO> getCurrentUser2(Authentication authentication) {
    MeDTO userMeDTO = userService.getCurrentUserDetails();
    return ResponseEntity.ok(userMeDTO);
}

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getUserById(@PathVariable UUID id) {
        ProfileDTO userDTO = userService.findProfileById(id);
        return ResponseEntity.ok(userDTO);
    }
}