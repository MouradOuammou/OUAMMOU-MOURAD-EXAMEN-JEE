package ouammou.backend.services;



import ouammou.backend.dtos.CreditDTO;
import ouammou.backend.entites.Credit;

import java.util.List;

public interface CreditService {
    List<CreditDTO> getAllCredits();
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getCreditsByClientId(Long clientId);
    List<CreditDTO> getCreditsByStatut(Credit.StatutCredit statut);
    CreditDTO createCredit(CreditDTO creditDTO);
    CreditDTO updateCredit(Long id, CreditDTO creditDTO);
    CreditDTO updateCreditStatut(Long id, Credit.StatutCredit statut);
    void deleteCredit(Long id);
    List<CreditDTO> getCreditPersonnelByMotif(String motif);

    List<CreditDTO> getCreditImmobilierByTypeBien(String typeBien);
    List<CreditDTO> getCreditProfessionnelByRaisonSociale(String raisonSociale);
}