package Arzanese.TrovaCasa.immobili;

import Arzanese.TrovaCasa.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImmobileRepository extends JpaRepository<Immobile, Long> {
    List<Immobile> findByUser(AppUser user);

}
