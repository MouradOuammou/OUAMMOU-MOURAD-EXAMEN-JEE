package ouammou.backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ouammou.backend.entites.Credit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private Date dateDemande;
    private Credit.StatutCredit statut;
    private Date dateAcception;
    private Double montant;
    private Integer duree;
    private Double tauxInteret;
    private Long clientId;
    private String type; 
    private String motif;
    private String typeBien; 
    private String raisonSociale; 
    private List<RemboursementDTO> remboursements = new ArrayList<>();
}
