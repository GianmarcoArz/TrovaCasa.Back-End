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
    public ResponseEntity<ImmagineImmobile> uploadImmagine(
            @PathVariable Long immobileId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "copertina", required = false, defaultValue = "false") boolean copertina
    ) throws IOException {
        ImmagineImmobile immagine = immagineImmobileService.uploadImmagine(
                immobileId,
                file,
                copertina
        );

        return ResponseEntity.ok(immagine);
    }






//    @GetMapping
//    public ResponseEntity<List<Immobile>> getAllImmobili() {
//        return new ResponseEntity<>(immobileService.getAllImmobili(), HttpStatus.OK);
//    }
//
//    @GetMapping("/singolo_immobile")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//
//    public ResponseEntity<Immobile> getImmobileById(@PathVariable Long id) {
//        return immobileService.getImmobileById(id)
//                .map(immobile -> new ResponseEntity<>(immobile, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping("/elimina_immobile")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//
//    public ResponseEntity<Void> deleteImmobile(@PathVariable Long id) {
//        immobileService.deleteImmobile(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/aggiorna_immobile")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//
//    public ResponseEntity<Immobile> updateImmobile(@RequestBody Immobile immobile) {
//        return ResponseEntity.ok(immobileService.updateImmobile(immobile));
//    }

}
