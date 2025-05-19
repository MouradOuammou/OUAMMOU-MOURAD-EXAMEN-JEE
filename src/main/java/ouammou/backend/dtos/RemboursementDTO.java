package ouammou.backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ouammou.backend.entites.Remboursement;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemboursementDTO {
    private Long id;
    private Date date;
    private Double montant;
    private Remboursement.TypeRemboursement type;
    private Long creditId;
}
