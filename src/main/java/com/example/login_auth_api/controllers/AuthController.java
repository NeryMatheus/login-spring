package com.example.login_auth_api.controllers;

import com.example.login_auth_api.dtos.LoginDTO;
import com.example.login_auth_api.dtos.RegisterUserDTO;
import com.example.login_auth_api.dtos.ResponseLoginDTO;
import com.example.login_auth_api.infra.security.TokenService;
import com.example.login_auth_api.model.User;
import com.example.login_auth_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody LoginDTO loginDTO) {
        User user = this.userRepository.findByEmail(loginDTO.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseLoginDTO(token, user.getName()));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseLoginDTO> register(@RequestBody RegisterUserDTO registerDTO) {
        Optional<User> userOptional = userRepository.findByEmail(registerDTO.email());

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        newUser.setName(registerDTO.name());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        ResponseLoginDTO response = new ResponseLoginDTO(token, newUser.getName());

        return ResponseEntity.ok(response);
    }
}
