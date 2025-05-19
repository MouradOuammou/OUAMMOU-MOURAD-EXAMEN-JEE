package ouammou.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouammou.backend.entites.CreditPersonnel;

import java.util.List;

@Repository
public interface CreditPersonnelRepository extends JpaRepository<CreditPersonnel, Long> {
    List<CreditPersonnel> findByMotif(String motif);
}