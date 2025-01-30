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

    public Immobile updateImmobile(ImmobileDTO immobileDTO, Long id) {
        // Recupero l'immobile esistente
        Immobile existingImmobile = immobileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Immobile non trovato con ID: " + id));

        // Verifica se l'immobile appartiene all'utente corrente
        AppUser currentUser = getCurrentUser();
        if (!existingImmobile.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("Non sei autorizzato a modificare questo immobile.");
        }

        // Aggiorna solo i campi modificabili utilizzando i dati dal DTO
        existingImmobile.setTitolo(immobileDTO.getTitolo());
        existingImmobile.setDescrizione(immobileDTO.getDescrizione());
        existingImmobile.setPrezzo(immobileDTO.getPrezzo());
        existingImmobile.setMetriQuadri(immobileDTO.getMetriQuadri());
        existingImmobile.setNumeroVani(immobileDTO.getNumeroVani());
        existingImmobile.setPiano(immobileDTO.getPiano());
        existingImmobile.setVia(immobileDTO.getVia());
        existingImmobile.setCivico(immobileDTO.getCivico());
        existingImmobile.setComune(immobileDTO.getComune());
        existingImmobile.setProvincia(immobileDTO.getProvincia());
        existingImmobile.setPostoAuto(immobileDTO.isPostoAuto());
        existingImmobile.setGiardino(immobileDTO.isGiardino());
        existingImmobile.setTerrazzo(immobileDTO.isTerrazzo());
        existingImmobile.setAscensore(immobileDTO.isAscensore());
        existingImmobile.setCantina(immobileDTO.isCantina());
        existingImmobile.setRiscaldamento(immobileDTO.isRiscaldamento());
        existingImmobile.setClimatizzazione(immobileDTO.isClimatizzazione());
        existingImmobile.setAllarme(immobileDTO.isAllarme());
        existingImmobile.setSorveglianza(immobileDTO.isSorveglianza());
        existingImmobile.setStatoImmobile(immobileDTO.getStatoImmobile());

        return immobileRepository.save(existingImmobile);
    }


    public void deleteImmobileById(Long id) {
        // Recupero l'immobile esistente
        Immobile immobile = immobileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Immobile non trovato con ID: " + id));

        // Verifica se l'immobile appartiene all'utente corrente
        AppUser currentUser = getCurrentUser();
        if (!immobile.getUser().getId().equals(currentUser.getId())) {
            throw new SecurityException("Non sei autorizzato a eliminare questo immobile.");
        }

        // Elimina l'immobile
        immobileRepository.deleteById(id);
    }

}
