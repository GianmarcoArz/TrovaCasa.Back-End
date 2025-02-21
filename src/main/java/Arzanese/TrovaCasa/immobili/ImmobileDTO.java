package Arzanese.TrovaCasa.immobili;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImmobileDTO {
    private String titolo;
    private String descrizione;
    private Integer prezzo;
    private Integer metriQuadri;
    private Integer numeroVani;
    private Integer piano;
    private String via;
    private Integer civico;
    private String comune;
    private String provincia;
    private boolean postoAuto;
    private boolean giardino;
    private boolean terrazzo;
    private boolean ascensore;
    private boolean cantina;
    private boolean riscaldamento;
    private boolean climatizzazione;
    private boolean allarme;
    private boolean sorveglianza;
    private StatoImmobile statoImmobile;

    private LocalDateTime dataDiInserimento;
    private Long userId;
}

