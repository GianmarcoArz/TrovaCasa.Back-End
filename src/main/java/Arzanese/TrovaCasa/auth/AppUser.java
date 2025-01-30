package Arzanese.TrovaCasa.auth;

import Arzanese.TrovaCasa.immobili.Immobile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String nome;
    private String cognome;
    private String telefono;

    @Column(name = "data_registrazione")
    private LocalDateTime dataRegistrazione;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Immobile> immobili;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Arzanese.TrovaCasa.auth.Role> roles;


}
