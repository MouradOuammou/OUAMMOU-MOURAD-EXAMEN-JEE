package ouammou.backend.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouammou.backend.dtos.AuthRequestDTO;
import ouammou.backend.dtos.AuthResponseDTO;
import ouammou.backend.dtos.UserDTO;
import ouammou.backend.services.AuthService;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "API pour l'authentification et l'enregistrement des utilisateurs")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Authentifier un utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authenticate(authRequestDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Crée un nouvel utilisateur et retourne ses détails")
    public ResponseEntity<UserDTO> register(
            @RequestBody UserDTO userDTO,
            @RequestParam String password) {
        return new ResponseEntity<>(authService.register(userDTO, password), HttpStatus.CREATED);
    }
}