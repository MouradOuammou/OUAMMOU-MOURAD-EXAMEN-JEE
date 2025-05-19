package ouammou.backend.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouammou.backend.dtos.ClientDTO;
import ouammou.backend.services.ClientService;


import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client Controller", description = "API pour gérer les clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les clients", description = "Retourne la liste de tous les clients")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un client par ID", description = "Retourne un client spécifique en fonction de son ID")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Récupérer un client par email", description = "Retourne un client spécifique en fonction de son email")
    public ResponseEntity<ClientDTO> getClientByEmail(@PathVariable String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau client", description = "Crée un nouveau client et retourne les détails du client créé")
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un client", description = "Met à jour les informations d'un client existant")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, clientDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un client", description = "Supprime un client spécifique en fonction de son ID")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
