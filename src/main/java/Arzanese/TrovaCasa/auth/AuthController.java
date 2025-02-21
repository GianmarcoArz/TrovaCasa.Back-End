package Arzanese.TrovaCasa.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;


    @PostMapping(path = "/register-user")
    public ResponseEntity<AppUser> registerUser(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(appUserService.registerUser(registerRequest, Set.of(Role.ROLE_USER)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = appUserService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(authResponse);
    }
}
