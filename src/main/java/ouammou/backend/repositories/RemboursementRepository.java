package ouammou.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ouammou.backend.entites.Remboursement;

import java.util.List;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    List<Remboursement> findByCreditId(Long creditId);
    List<Remboursement> findByType(Remboursement.TypeRemboursement type);
}