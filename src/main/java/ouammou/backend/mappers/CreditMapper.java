package ouammou.backend.mappers;


import org.springframework.stereotype.Component;
import ouammou.backend.dtos.CreditDTO;
import ouammou.backend.entites.Credit;
import ouammou.backend.entites.CreditImmobilier;
import ouammou.backend.entites.CreditPersonnel;
import ouammou.backend.entites.CreditProfessionnel;

import java.util.stream.Collectors;

@Component
public class CreditMapper {

    private final RemboursementMapper remboursementMapper;

    public CreditMapper(RemboursementMapper remboursementMapper) {
        this.remboursementMapper = remboursementMapper;
    }

    public CreditDTO toDTO(Credit credit) {
        if (credit == null) {
            return null;
        }

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setId(credit.getId());
        creditDTO.setDateDemande(credit.getDateDemande());
        creditDTO.setStatut(credit.getStatut());
        creditDTO.setDateAcception(credit.getDateAcception());
        creditDTO.setMontant(credit.getMontant());
        creditDTO.setDuree(credit.getDuree());
        creditDTO.setTauxInteret(credit.getTauxInteret());

        if (credit.getClient() != null) {
            creditDTO.setClientId(credit.getClient().getId());
        }

        if (credit.getRemboursements() != null) {
            creditDTO.setRemboursements(credit.getRemboursements().stream()
                    .map(remboursementMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (credit instanceof CreditPersonnel) {
            creditDTO.setType("PERSONNEL");
            creditDTO.setMotif(((CreditPersonnel) credit).getMotif());
        } else if (credit instanceof CreditImmobilier) {
            creditDTO.setType("IMMOBILIER");
            creditDTO.setTypeBien(((CreditImmobilier) credit).getTypeBien().name());
        } else if (credit instanceof CreditProfessionnel) {
            creditDTO.setType("PROFESSIONNEL");
            creditDTO.setMotif(((CreditProfessionnel) credit).getMotif());
            creditDTO.setRaisonSociale(((CreditProfessionnel) credit).getRaisonSociale());
        }

        return creditDTO;
    }

    public Credit toEntity(CreditDTO creditDTO) {
        if (creditDTO == null) {
            return null;
        }

        Credit credit;

        if ("PERSONNEL".equals(creditDTO.getType())) {
            CreditPersonnel creditPersonnel = new CreditPersonnel();
            creditPersonnel.setMotif(creditDTO.getMotif());
            credit = creditPersonnel;
        } else if ("IMMOBILIER".equals(creditDTO.getType())) {
            CreditImmobilier creditImmobilier = new CreditImmobilier();
            if (creditDTO.getTypeBien() != null) {
                creditImmobilier.setTypeBien(CreditImmobilier.TypeBien.valueOf(creditDTO.getTypeBien()));
            }
            credit = creditImmobilier;
        } else if ("PROFESSIONNEL".equals(creditDTO.getType())) {
            CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
            creditProfessionnel.setMotif(creditDTO.getMotif());
            creditProfessionnel.setRaisonSociale(creditDTO.getRaisonSociale());
            credit = creditProfessionnel;
        } else {
            credit = new Credit();
        }

        credit.setId(creditDTO.getId());
        credit.setDateDemande(creditDTO.getDateDemande());
        credit.setStatut(creditDTO.getStatut());
        credit.setDateAcception(creditDTO.getDateAcception());
        credit.setMontant(creditDTO.getMontant());
        credit.setDuree(creditDTO.getDuree());
        credit.setTauxInteret(creditDTO.getTauxInteret());

        return credit;
    }
}