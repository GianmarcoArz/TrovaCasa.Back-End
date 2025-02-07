package Arzanese.TrovaCasa.immobili;

import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.immobili.immagini_immobili.ImmagineImmobile;
import Arzanese.TrovaCasa.immobili.immagini_immobili.ImmagineImmobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/immobili")
public class ImmobileController {
    @Autowired
    private ImmobileService immobileService;

    @Autowired
    private ImmagineImmobileService immagineImmobileService;

    @PostMapping("/crea_annuncio_immobile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Immobile> creaAnnuncioImmobile(@RequestBody ImmobileDTO immobileDTO) {
        Immobile immobile = new Immobile();
        immobile.setTitolo(immobileDTO.getTitolo());
        immobile.setDescrizione(immobileDTO.getDescrizione());
        immobile.setStatoImmobile(immobileDTO.getStatoImmobile());
        immobile.setPrezzo(immobileDTO.getPrezzo());
        immobile.setMetriQuadri(immobileDTO.getMetriQuadri());
        immobile.setNumeroVani(immobileDTO.getNumeroVani());
        immobile.setPiano(immobileDTO.getPiano());
        immobile.setVia(immobileDTO.getVia());
        immobile.setCivico(immobileDTO.getCivico());
        immobile.setComune(immobileDTO.getComune());
        immobile.setProvincia(immobileDTO.getProvincia());
        immobile.setPostoAuto(immobileDTO.isPostoAuto());
        immobile.setGiardino(immobileDTO.isGiardino());
        immobile.setTerrazzo(immobileDTO.isTerrazzo());
        immobile.setAscensore(immobileDTO.isAscensore());
        immobile.setCantina(immobileDTO.isCantina());
        immobile.setRiscaldamento(immobileDTO.isRiscaldamento());
        immobile.setClimatizzazione(immobileDTO.isClimatizzazione());
        immobile.setAllarme(immobileDTO.isAllarme());
        immobile.setSorveglianza(immobileDTO.isSorveglianza());
        immobile.setDataDiInserimento(LocalDateTime.now());

        // Ottieni l'utente corrente e associa l'immobile a questo utente
        AppUser currentUser = immobileService.getCurrentUser();
        immobile.setUser(currentUser);

        return ResponseEntity.ok(immobileService.save(immobile));
    }

    @PostMapping(value = "/{immobileId}/upload_immagine", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<ImmagineImmobile>> uploadImmagini(
            @PathVariable Long immobileId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "copertina", required = false, defaultValue = "false") boolean copertina
    ) throws IOException {
        List<ImmagineImmobile> immagini = immagineImmobileService.uploadImmagini(
                immobileId,
                files,
                copertina
        );

        return ResponseEntity.ok(immagini);
    }


    @PutMapping("/aggiorna_immobile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Immobile> updateImmobile(@RequestBody ImmobileDTO immobileDTO, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(immobileService.updateImmobile(immobileDTO, id));
        } catch (SecurityException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }


    @DeleteMapping("/elimina_immobile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteImmobile(@PathVariable Long id) {
        try {
            immobileService.deleteImmobileById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/lista_Immobili")
    public ResponseEntity<List<Immobile>> getAllImmobili() {
        return new ResponseEntity<>(immobileService.getAllImmobili(), HttpStatus.OK);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<Immobile>> getImmobiliByCurrentUser() {
        return new ResponseEntity<>(immobileService.getImmobiliByCurrentUser(), HttpStatus.OK);
    }
    @GetMapping("/{immobileId}")
    public ResponseEntity<Immobile> getImmobileById(@PathVariable Long id) {
        Immobile immobile = immobileService.getImmobileById(id);
        return ResponseEntity.ok(immobile);
    }
}
