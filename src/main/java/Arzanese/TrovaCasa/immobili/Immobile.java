package Arzanese.TrovaCasa.immobili;

import Arzanese.TrovaCasa.appuntamenti.Appuntamento;
import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.auth.TipoUser;
import Arzanese.TrovaCasa.immobili.immagini_immobili.ImmagineImmobile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "immobili")
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private Integer prezzo;

    @Column(name = "mq", nullable = false)
    private Integer metriQuadri;

    @Column(name = "vani", nullable = false)
    private Integer numeroVani;

    @Column(nullable = false)
    private Integer piano;

    @Column(nullable = false)
    private String via;
    @Column(nullable = false)
    private Integer civico;
    @Column(nullable = false)
    private String comune;
    @Column(nullable = false)
    private String provincia;
    @Column(nullable = false)
    private boolean postoAuto;

    private boolean giardino;
    private boolean terrazzo;
    private boolean ascensore;
    private boolean cantina;
    private boolean riscaldamento;
    private boolean climatizzazione;
    private boolean allarme;
    private boolean sorveglianza;

    @Column(name = "data_di_inserimento")
    private LocalDateTime dataDiInserimento;

    @Column(name = "tipo_cliente")

    @Enumerated(EnumType.STRING)
    private TipoUser tipoUser;

    @Column(name = "stato_immobile")
    @Enumerated(EnumType.STRING)
    private StatoImmobile statoImmobile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "immobile",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ImmagineImmobile> immagini;

    @OneToMany(mappedBy = "immobile",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Appuntamento> disponibilita;
}
