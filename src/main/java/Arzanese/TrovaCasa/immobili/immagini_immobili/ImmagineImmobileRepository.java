package Arzanese.TrovaCasa.immobili.immagini_immobili;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImmagineImmobileRepository extends JpaRepository<ImmagineImmobile, Long> {
    @Query("SELECT i FROM ImmagineImmobile i WHERE i.immobile.id = :immobileId")
    List<ImmagineImmobile> findByImmobileId(@Param("immobileId") Long immobileId);

}


