package Arzanese.TrovaCasa.immobili.immagini_immobili;

import Arzanese.TrovaCasa.immobili.Immobile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "immagini_immobili")
public class ImmagineImmobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_immagine")
    private String urlImmagine;

    @Column(name = "copertina")
    private String copertina;

    @ManyToOne
    @JoinColumn(name = "immobile_id")
    @JsonBackReference
    private Immobile immobile;
}
