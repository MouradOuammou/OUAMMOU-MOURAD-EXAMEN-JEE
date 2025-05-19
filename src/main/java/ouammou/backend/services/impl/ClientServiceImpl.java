package ouammou.backend.services.impl;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ouammou.backend.dtos.ClientDTO;
import ouammou.backend.entites.Client;
import ouammou.backend.mappers.ClientMapper;
import ouammou.backend.repositories.ClientRepository;
import ouammou.backend.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Client n'existe pas avec id: " + id));
    }

    @Override
    public ClientDTO getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(clientMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Client n'existe pas avec email: " + email));
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client n'exists pas avec  id: " + id);
        }

        Client client = clientMapper.toEntity(clientDTO);
        client.setId(id);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDTO(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client n'existe pas avec id : " + id);
        }
        clientRepository.deleteById(id);
    }
}