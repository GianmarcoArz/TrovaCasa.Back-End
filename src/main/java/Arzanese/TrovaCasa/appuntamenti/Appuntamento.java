package Arzanese.TrovaCasa.appuntamenti;

import Arzanese.TrovaCasa.auth.AppUser;
import Arzanese.TrovaCasa.immobili.Immobile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "appuntamenti")
public class Appuntamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_disponibilita")
    private String dataDisponibilita;

    @Column(name = "ora_inizio")
    private String oraInizio;

    @Column(name = "ora_fine")
    private String oraFine;

    @Column(name = "e_prenotato")
    private Boolean prenotato;

    @ManyToOne
    @JoinColumn(name = "immobile_id")
    @JsonBackReference
    private Immobile immobile;


    @ManyToOne
    @JoinColumn(name = "creatore_annuncio_id")
    private AppUser creatoreAnnuncio; // Utente che ha creato l'annuncio

    @ManyToOne
    @JoinColumn(name = "utente_prenotato_id")
    private AppUser utentePrenotato; // Utente che effettua la prenotazione

}
