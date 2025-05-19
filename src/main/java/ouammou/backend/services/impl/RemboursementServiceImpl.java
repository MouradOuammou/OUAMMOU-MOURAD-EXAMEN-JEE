package ouammou.backend.services.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ouammou.backend.dtos.RemboursementDTO;
import ouammou.backend.entites.Credit;
import ouammou.backend.entites.Remboursement;
import ouammou.backend.mappers.RemboursementMapper;
import ouammou.backend.repositories.*;
import ouammou.backend.services.RemboursementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RemboursementServiceImpl implements RemboursementService {

    private final RemboursementRepository remboursementRepository;
    private final CreditRepository creditRepository;
    private final RemboursementMapper remboursementMapper;

    public RemboursementServiceImpl(
            RemboursementRepository remboursementRepository,
            CreditRepository creditRepository,
            RemboursementMapper remboursementMapper) {
        this.remboursementRepository = remboursementRepository;
        this.creditRepository = creditRepository;
        this.remboursementMapper = remboursementMapper;
    }

    @Override
    public List<RemboursementDTO> getAllRemboursements() {
        return remboursementRepository.findAll().stream()
                .map(remboursementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RemboursementDTO getRemboursementById(Long id) {
        return remboursementRepository.findById(id)
                .map(remboursementMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Remboursement not found with id: " + id));
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByCreditId(Long creditId) {
        return remboursementRepository.findByCreditId(creditId).stream()
                .map(remboursementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RemboursementDTO> getRemboursementsByType(Remboursement.TypeRemboursement type) {
        return remboursementRepository.findByType(type).stream()
                .map(remboursementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RemboursementDTO createRemboursement(RemboursementDTO remboursementDTO) {
        Remboursement remboursement = remboursementMapper.toEntity(remboursementDTO);

        // Set the credit
        if (remboursementDTO.getCreditId() != null) {
            Credit credit = creditRepository.findById(remboursementDTO.getCreditId())
                    .orElseThrow(() -> new EntityNotFoundException("Credit n'existe pas avec 'id: " + remboursementDTO.getCreditId()));
            remboursement.setCredit(credit);
        }

        Remboursement savedRemboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.toDTO(savedRemboursement);
    }

    @Override
    @Transactional
    public RemboursementDTO updateRemboursement(Long id, RemboursementDTO remboursementDTO) {
        if (!remboursementRepository.existsById(id)) {
            throw new EntityNotFoundException("Remboursement n'existe pas avec  id: " + id);
        }

        Remboursement remboursement = remboursementMapper.toEntity(remboursementDTO);
        remboursement.setId(id);

        // Set the credit
        if (remboursementDTO.getCreditId() != null) {
            Credit credit = creditRepository.findById(remboursementDTO.getCreditId())
                    .orElseThrow(() -> new EntityNotFoundException("Credit n'existe pas avec  id: " + remboursementDTO.getCreditId()));
            remboursement.setCredit(credit);
        }

        Remboursement updatedRemboursement = remboursementRepository.save(remboursement);
        return remboursementMapper.toDTO(updatedRemboursement);
    }

    @Override
    public void deleteRemboursement(Long id) {
        if (!remboursementRepository.existsById(id)) {
            throw new EntityNotFoundException("Remboursement n'existe pas avec id: " + id);
        }
        remboursementRepository.deleteById(id);
    }
}