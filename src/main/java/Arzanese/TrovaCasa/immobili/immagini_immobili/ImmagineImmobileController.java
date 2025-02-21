package Arzanese.TrovaCasa.immobili.immagini_immobili;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/immagini_immobili")
public class ImmagineImmobileController {

    @Autowired
    private ImmagineImmobileService immagineImmobileService;

    @GetMapping("/{immobileId}")
    public ResponseEntity<List<ImmagineImmobileDTO>> getImmaginiByImmobileId(@PathVariable Long immobileId) {
        List<ImmagineImmobileDTO> immagini = immagineImmobileService.getImmaginiByImmobileId(immobileId);
        return ResponseEntity.ok(immagini);
    }
}
