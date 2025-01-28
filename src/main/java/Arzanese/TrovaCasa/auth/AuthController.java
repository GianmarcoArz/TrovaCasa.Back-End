package Arzanese.TrovaCasa.auth;

import Arzanese.TrovaCasa.auth.AppUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

//    @PostMapping(path = "/register-user")
//    public ResponseEntity<AppUser> registerUser(@RequestParam("appUser") String appUser){
//        ObjectMapper objectMapper = new ObjectMapper();
//        RegisterRequest registerRequest;
//
//        try {
//            registerRequest = objectMapper.readValue(appUser, RegisterRequest.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        return new ResponseEntity<>(appUserService.registerUser(registerRequest, Set.of(Role.ROLE_USER)), HttpStatus.CREATED);
//    }

    @PostMapping(path = "/register-user")
    public ResponseEntity<AppUser> registerUser(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(appUserService.registerUser(registerRequest, Set.of(Role.ROLE_USER)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Arzanese.TrovaCasa.auth.AuthResponse> login(@RequestBody Arzanese.TrovaCasa.auth.LoginRequest loginRequest) {
        String token = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(new Arzanese.TrovaCasa.auth.AuthResponse(token));
    }
}
