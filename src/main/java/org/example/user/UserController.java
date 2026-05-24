package org.example.user;

import org.example.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest()
                    .body("Erro: Username e password são obrigatórios.");
        }

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // TODO: usar BCryptPasswordEncoder em produção
            if (user.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "type", "Bearer",
                        "username", user.getUsername(),
                        "role", user.getRole()
                ));
            }
        }

        return ResponseEntity.status(401)
                .body("Credenciais inválidas. Acesso negado.");
    }
}