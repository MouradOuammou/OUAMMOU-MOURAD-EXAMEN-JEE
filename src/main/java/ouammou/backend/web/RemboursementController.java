package ouammou.backend.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouammou.backend.dtos.RemboursementDTO;
import ouammou.backend.entites.Remboursement;
import ouammou.backend.services.RemboursementService;

import java.util.List;

@RestController
@RequestMapping("/api/remboursements")
@Tag(name = "Remboursement Controller", description = "API pour gérer les remboursements")
public class RemboursementController {

    private final RemboursementService remboursementService;

    public RemboursementController(RemboursementService remboursementService) {
        this.remboursementService = remboursementService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les remboursements", description = "Retourne la liste de tous les remboursements")
    public ResponseEntity<List<RemboursementDTO>> getAllRemboursements() {
        return ResponseEntity.ok(remboursementService.getAllRemboursements());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un remboursement par ID", description = "Retourne un remboursement spécifique en fonction de son ID")
    public ResponseEntity<RemboursementDTO> getRemboursementById(@PathVariable Long id) {
        return ResponseEntity.ok(remboursementService.getRemboursementById(id));
    }

    @GetMapping("/credit/{creditId}")
    @Operation(summary = "Récupérer les remboursements d'un crédit", description = "Retourne tous les remboursements associés à un crédit spécifique")
    public ResponseEntity<List<RemboursementDTO>> getRemboursementsByCreditId(@PathVariable Long creditId) {
        return ResponseEntity.ok(remboursementService.getRemboursementsByCreditId(creditId));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Récupérer les remboursements par type", description = "Retourne tous les remboursements d'un type spécifique")
    public ResponseEntity<List<RemboursementDTO>> getRemboursementsByType(@PathVariable Remboursement.TypeRemboursement type) {
        return ResponseEntity.ok(remboursementService.getRemboursementsByType(type));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau remboursement", description = "Crée un nouveau remboursement et retourne les détails du remboursement créé")
    public ResponseEntity<RemboursementDTO> createRemboursement(@RequestBody RemboursementDTO remboursementDTO) {
        return new ResponseEntity<>(remboursementService.createRemboursement(remboursementDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un remboursement", description = "Met à jour les informations d'un remboursement existant")
    public ResponseEntity<RemboursementDTO> updateRemboursement(@PathVariable Long id, @RequestBody RemboursementDTO remboursementDTO) {
        return ResponseEntity.ok(remboursementService.updateRemboursement(id, remboursementDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un remboursement", description = "Supprime un remboursement spécifique en fonction de son ID")
    public ResponseEntity<Void> deleteRemboursement(@PathVariable Long id) {
        remboursementService.deleteRemboursement(id);
        return ResponseEntity.noContent().build();
    }
}
