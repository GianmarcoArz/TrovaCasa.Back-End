package Arzanese.TrovaCasa.immobili;

import Arzanese.TrovaCasa.auth_and_users.User;
import Arzanese.TrovaCasa.appuntamenti.Appuntamento;
import Arzanese.TrovaCasa.immobili.immagini_immobili.ImmagineImmobile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String titolo;
    private String descrizione;
    private String prezzo;

    @Column(name = "mq")
    private String metriQuadri;

    @Column(name = "vani")
    private String numeroVani;

    private Integer piano;
    private String via;
    private String civico;
    private String comune;
    private String provincia;

    @Column(name = "data_di_inserimento")
    private String dataDiInserimento;

    @Column(name = "in_evidenza")
    private Boolean inEvidenza;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "immobile",cascade = CascadeType.ALL)
    private List<ImmagineImmobile> immagini;

    @OneToMany(mappedBy = "immobile",cascade = CascadeType.ALL)
    private List<Appuntamento> disponibilita;
}
