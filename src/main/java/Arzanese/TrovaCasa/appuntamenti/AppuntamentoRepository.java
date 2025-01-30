package Arzanese.TrovaCasa.appuntamenti;

import Arzanese.TrovaCasa.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {
    List<Appuntamento> findByCreatoreAnnuncioAndPrenotatoTrue(AppUser creatoreAnnuncio);
}
