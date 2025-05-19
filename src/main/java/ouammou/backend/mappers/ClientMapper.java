package ouammou.backend.mappers;


import org.springframework.stereotype.Component;
import ouammou.backend.dtos.ClientDTO;
import ouammou.backend.entites.Client;

import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private final CreditMapper creditMapper;

    public ClientMapper(CreditMapper creditMapper) {
        this.creditMapper = creditMapper;
    }

    public ClientDTO toDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setNom(client.getNom());
        clientDTO.setEmail(client.getEmail());

        if (client.getCredits() != null) {
            clientDTO.setCredits(client.getCredits().stream()
                    .map(creditMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return clientDTO;
    }

    public Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());

        return client;
    }
}