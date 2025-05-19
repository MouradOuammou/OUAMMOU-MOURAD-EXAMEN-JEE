package ouammou.backend.mappers;

import org.springframework.stereotype.Component;
import ouammou.backend.dtos.RemboursementDTO;
import ouammou.backend.entites.Remboursement;


@Component
public class RemboursementMapper {

    public RemboursementDTO toDTO(Remboursement remboursement) {
        if (remboursement == null) {
            return null;
        }

        RemboursementDTO remboursementDTO = new RemboursementDTO();
        remboursementDTO.setId(remboursement.getId());
        remboursementDTO.setDate(remboursement.getDate());
        remboursementDTO.setMontant(remboursement.getMontant());
        remboursementDTO.setType(remboursement.getType());

        if (remboursement.getCredit() != null) {
            remboursementDTO.setCreditId(remboursement.getCredit().getId());
        }

        return remboursementDTO;
    }

    public Remboursement toEntity(RemboursementDTO remboursementDTO) {
        if (remboursementDTO == null) {
            return null;
        }

        Remboursement remboursement = new Remboursement();
        remboursement.setId(remboursementDTO.getId());
        remboursement.setDate(remboursementDTO.getDate());
        remboursement.setMontant(remboursementDTO.getMontant());
        remboursement.setType(remboursementDTO.getType());

        return remboursement;
    }
}
