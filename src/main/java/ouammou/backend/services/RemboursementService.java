package ouammou.backend.services;

import ouammou.backend.dtos.RemboursementDTO;
import ouammou.backend.entites.Remboursement;

import java.util.List;

public interface RemboursementService {
    List<RemboursementDTO> getAllRemboursements();
    RemboursementDTO getRemboursementById(Long id);
    List<RemboursementDTO> getRemboursementsByCreditId(Long creditId);
    List<RemboursementDTO> getRemboursementsByType(Remboursement.TypeRemboursement type);
    RemboursementDTO createRemboursement(RemboursementDTO remboursementDTO);
    RemboursementDTO updateRemboursement(Long id, RemboursementDTO remboursementDTO);
    void deleteRemboursement(Long id);
}
