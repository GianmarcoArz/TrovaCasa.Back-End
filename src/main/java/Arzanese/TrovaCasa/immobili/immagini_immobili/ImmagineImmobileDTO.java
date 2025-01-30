package Arzanese.TrovaCasa.immobili.immagini_immobili;

import lombok.Data;

@Data
public class ImmagineImmobileDTO {
    private Long id;
    private String urlImmagine;
    private String copertina;
    private Long immobileId;
}
