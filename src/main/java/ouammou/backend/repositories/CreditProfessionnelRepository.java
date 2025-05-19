package ouammou.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouammou.backend.entites.CreditProfessionnel;

import java.util.List;

@Repository
public interface CreditProfessionnelRepository extends JpaRepository<CreditProfessionnel, Long> {
    List<CreditProfessionnel> findByRaisonSociale(String raisonSociale);
}
