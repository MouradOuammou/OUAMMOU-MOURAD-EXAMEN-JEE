package ouammou.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouammou.backend.entites.CreditImmobilier;

import java.util.List;

@Repository
public interface CreditImmobilierRepository extends JpaRepository<CreditImmobilier, Long> {
    List<CreditImmobilier> findByTypeBien(CreditImmobilier.TypeBien typeBien);
}
