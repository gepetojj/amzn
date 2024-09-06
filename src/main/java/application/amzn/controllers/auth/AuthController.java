package application.amzn.controllers.auth;

import application.amzn.entities.User;
import application.amzn.exceptions.primitives.BadRequestException;
import application.amzn.repositories.UserRepository;
import application.amzn.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        var userAndPassToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(userAndPassToken);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        if (this.userRepository.findByEmail(dto.email()) != null) {
            throw new BadRequestException("O email informado já existe.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.name(), dto.email(), encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        var user = userRepository.findUserByEmail(dto.email());
        if (user == null) {
            throw new BadRequestException("Não foi encontrado usuário com o email informado.");
        }
        var userAndPassToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        authenticationManager.authenticate(userAndPassToken);

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.newPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
