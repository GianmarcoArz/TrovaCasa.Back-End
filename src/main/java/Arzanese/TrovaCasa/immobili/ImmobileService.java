package Arzanese.TrovaCasa.immobili;

import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.auth.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ImmobileService {



    @Autowired
    private ImmobileRepository immobileRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser getCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));
    }

    public Immobile save(Immobile immobile) {
        return immobileRepository.save(immobile);
    }

    public Optional<Immobile> getImmobileById(Long id) {
        return immobileRepository.findById(id);
    }

    public void deleteImmobile(Long id) {
        immobileRepository.deleteById(id);
    }

    public Immobile updateImmobile(Immobile immobile) {
        return immobileRepository.save(immobile);
    }

    public List<Immobile> getAllImmobili() {
        return immobileRepository.findAll();
    }




}
