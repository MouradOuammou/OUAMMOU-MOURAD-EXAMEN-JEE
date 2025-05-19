package ouammou.backend.services.impl;



import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ouammou.backend.dtos.CreditDTO;
import ouammou.backend.entites.*;
import ouammou.backend.mappers.CreditMapper;
import ouammou.backend.repositories.*;
import ouammou.backend.services.CreditService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CreditPersonnelRepository creditPersonnelRepository;
    private final CreditImmobilierRepository creditImmobilierRepository;
    private final CreditProfessionnelRepository creditProfessionnelRepository;
    private final ClientRepository clientRepository;
    private final CreditMapper creditMapper;

    public CreditServiceImpl(
            CreditRepository creditRepository,
            CreditPersonnelRepository creditPersonnelRepository,
            CreditImmobilierRepository creditImmobilierRepository,
            CreditProfessionnelRepository creditProfessionnelRepository,
            ClientRepository clientRepository,
            CreditMapper creditMapper) {
        this.creditRepository = creditRepository;
        this.creditPersonnelRepository = creditPersonnelRepository;
        this.creditImmobilierRepository = creditImmobilierRepository;
        this.creditProfessionnelRepository = creditProfessionnelRepository;
        this.clientRepository = clientRepository;
        this.creditMapper = creditMapper;
    }

    @Override
    public List<CreditDTO> getAllCredits() {
        return creditRepository.findAll().stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO getCreditById(Long id) {
        return creditRepository.findById(id)
                .map(creditMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Credit n'existe pas avec id: " + id));
    }

    @Override
    public List<CreditDTO> getCreditsByClientId(Long clientId) {
        return creditRepository.findByClientId(clientId).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByStatut(Credit.StatutCredit statut) {
        return creditRepository.findByStatut(statut).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CreditDTO createCredit(CreditDTO creditDTO) {
        Credit credit = creditMapper.toEntity(creditDTO);

        // Set the client
        if (creditDTO.getClientId() != null) {
            Client client = clientRepository.findById(creditDTO.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client n'existe pas avec id: " + creditDTO.getClientId()));
            credit.setClient(client);
        }

        Credit savedCredit;

        if (credit instanceof CreditPersonnel) {
            savedCredit = creditPersonnelRepository.save((CreditPersonnel) credit);
        } else if (credit instanceof CreditImmobilier) {
            savedCredit = creditImmobilierRepository.save((CreditImmobilier) credit);
        } else if (credit instanceof CreditProfessionnel) {
            savedCredit = creditProfessionnelRepository.save((CreditProfessionnel) credit);
        } else {
            savedCredit = creditRepository.save(credit);
        }

        return creditMapper.toDTO(savedCredit);
    }

    @Override
    @Transactional
    public CreditDTO updateCredit(Long id, CreditDTO creditDTO) {
        if (!creditRepository.existsById(id)) {
            throw new EntityNotFoundException("Credit n'existe pas avec id: " + id);
        }

        Credit credit = creditMapper.toEntity(creditDTO);
        credit.setId(id);

        if (creditDTO.getClientId() != null) {
            Client client = clientRepository.findById(creditDTO.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client n'existe pas avec id: " + creditDTO.getClientId()));
            credit.setClient(client);
        }

        Credit updatedCredit;

        if (credit instanceof CreditPersonnel) {
            updatedCredit = creditPersonnelRepository.save((CreditPersonnel) credit);
        } else if (credit instanceof CreditImmobilier) {
            updatedCredit = creditImmobilierRepository.save((CreditImmobilier) credit);
        } else if (credit instanceof CreditProfessionnel) {
            updatedCredit = creditProfessionnelRepository.save((CreditProfessionnel) credit);
        } else {
            updatedCredit = creditRepository.save(credit);
        }

        return creditMapper.toDTO(updatedCredit);
    }

    @Override
    @Transactional
    public CreditDTO updateCreditStatut(Long id, Credit.StatutCredit statut) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Credit n'existe pas avec id: " + id));

        credit.setStatut(statut);
        Credit updatedCredit = creditRepository.save(credit);

        return creditMapper.toDTO(updatedCredit);
    }

    @Override
    public void deleteCredit(Long id) {
        if (!creditRepository.existsById(id)) {
            throw new EntityNotFoundException("Credit n'existe pas avecid: " + id);
        }
        creditRepository.deleteById(id);
    }

    @Override
    public List<CreditDTO> getCreditPersonnelByMotif(String motif) {
        return creditPersonnelRepository.findByMotif(motif).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditImmobilierByTypeBien(String typeBien) {
        try {
            CreditImmobilier.TypeBien typeBienEnum = CreditImmobilier.TypeBien.valueOf(typeBien);
            return creditImmobilierRepository.findByTypeBien(typeBienEnum).stream()
                    .map(creditMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid type bien: " + typeBien);
        }
    }

    @Override
    public List<CreditDTO> getCreditProfessionnelByRaisonSociale(String raisonSociale) {
        return creditProfessionnelRepository.findByRaisonSociale(raisonSociale).stream()
                .map(creditMapper::toDTO)
                .collect(Collectors.toList());
    }
}
