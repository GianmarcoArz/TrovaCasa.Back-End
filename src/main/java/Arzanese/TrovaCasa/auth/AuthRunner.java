package Arzanese.TrovaCasa.auth;

import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            RegisterRequest adminRequest = new RegisterRequest();
            adminRequest.setUsername("admin");
            adminRequest.setPassword("adminpwd");
            adminRequest.setEmail("admin@info.it");

            appUserService.registerUser(adminRequest, Set.of(Role.ROLE_ADMIN));
        // Creazione dell'utente user se non esiste
//        Optional<AppUser> normalUser = appUserService.findByUsername("user");
//        if (normalUser.isEmpty()) {
//            RegisterRequest userRequest = new RegisterRequest();
//            userRequest.setUsername("");
//            userRequest.setPassword("");
//            userRequest.setEmail("");
//
//            appUserService.registerUser(userRequest, Set.of(Role.ROLE_USER));
//        }
        }
    }
}

