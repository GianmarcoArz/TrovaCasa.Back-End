package Arzanese.TrovaCasa.appuntamenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appuntamenti")
public class AppuntamentoController {

    @Autowired
    private AppuntamentoService appuntamentoService;


    @PostMapping("/{immobileId}/crea_disponibilita")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Appuntamento> creaDisponibilita(
            @PathVariable Long immobileId,
            @RequestBody AppuntamentoDTO appuntamentoDTO
    ) {
        // Passa immobileId e il DTO al servizio
        Appuntamento appuntamento = appuntamentoService.creaDisponibilita(immobileId, appuntamentoDTO);
        return ResponseEntity.ok(appuntamento);
    }


    @PostMapping("/{appuntamentoId}/prenota_disponibilita")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Appuntamento> prenota(@PathVariable Long appuntamentoId) {
        Appuntamento appuntamento = appuntamentoService.prenotaAppuntamento(appuntamentoId);
        return ResponseEntity.ok(appuntamento);
    }

    @GetMapping("/vedi_prenotazioni")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<Appuntamento>> getAppuntamentiPrenotatiCreatore() {
        List<Appuntamento> appuntamentiPrenotati = appuntamentoService.getAppuntamentiPrenotatiCreatore();
        return ResponseEntity.ok(appuntamentiPrenotati);
    }

    @PutMapping("/{appuntamentoId}/aggiorna_disponibilita")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Appuntamento> aggiornaDisponibilita(
            @PathVariable Long appuntamentoId,
            @RequestBody AppuntamentoDTO appuntamentoDTO
    ) {
        // Passa l'ID dell'appuntamento e il DTO al servizio
        Appuntamento appuntamentoAggiornato = appuntamentoService.aggiornaDisponibilita(appuntamentoId, appuntamentoDTO);
        return ResponseEntity.ok(appuntamentoAggiornato);
    }

    @DeleteMapping("/{appuntamentoId}/elimina_disponibilita")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Void> eliminaDisponibilita(@PathVariable Long appuntamentoId) {
        // Richiama il servizio per eliminare l'appuntamento
        appuntamentoService.eliminaDisponibilita(appuntamentoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}




