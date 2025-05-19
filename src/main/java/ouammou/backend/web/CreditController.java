package ouammou.backend.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ouammou.backend.dtos.CreditDTO;
import ouammou.backend.entites.Credit;
import ouammou.backend.services.CreditService;


import java.util.List;

@RestController
@RequestMapping("/api/credits")
@Tag(name = "Credit Controller", description = "API pour gérer les crédits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les crédits", description = "Retourne la liste de tous les crédits")
    public ResponseEntity<List<CreditDTO>> getAllCredits() {
        return ResponseEntity.ok(creditService.getAllCredits());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un crédit par ID", description = "Retourne un crédit spécifique en fonction de son ID")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.getCreditById(id));
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Récupérer les crédits d'un client", description = "Retourne tous les crédits associés à un client spécifique")
    public ResponseEntity<List<CreditDTO>> getCreditsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(creditService.getCreditsByClientId(clientId));
    }

    @GetMapping("/statut/{statut}")
    @Operation(summary = "Récupérer les crédits par statut", description = "Retourne tous les crédits ayant un statut spécifique")
    public ResponseEntity<List<CreditDTO>> getCreditsByStatut(@PathVariable Credit.StatutCredit statut) {
        return ResponseEntity.ok(creditService.getCreditsByStatut(statut));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau crédit", description = "Crée un nouveau crédit et retourne les détails du crédit créé")
    public ResponseEntity<CreditDTO> createCredit(@RequestBody CreditDTO creditDTO) {
        return new ResponseEntity<>(creditService.createCredit(creditDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un crédit", description = "Met à jour les informations d'un crédit existant")
    public ResponseEntity<CreditDTO> updateCredit(@PathVariable Long id, @RequestBody CreditDTO creditDTO) {
        return ResponseEntity.ok(creditService.updateCredit(id, creditDTO));
    }

    @PatchMapping("/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'un crédit", description = "Met à jour uniquement le statut d'un crédit existant")
    public ResponseEntity<CreditDTO> updateCreditStatut(@PathVariable Long id, @RequestParam Credit.StatutCredit statut) {
        return ResponseEntity.ok(creditService.updateCreditStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un crédit", description = "Supprime un crédit spécifique en fonction de son ID")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints spécifiques pour les différents types de crédits

    @GetMapping("/personnel/motif/{motif}")
    @Operation(summary = "Récupérer les crédits personnels par motif", description = "Retourne tous les crédits personnels ayant un motif spécifique")
    public ResponseEntity<List<CreditDTO>> getCreditPersonnelByMotif(@PathVariable String motif) {
        return ResponseEntity.ok(creditService.getCreditPersonnelByMotif(motif));
    }

    @GetMapping("/immobilier/typeBien/{typeBien}")
    @Operation(summary = "Récupérer les crédits immobiliers par type de bien", description = "Retourne tous les crédits immobiliers pour un type de bien spécifique")
    public ResponseEntity<List<CreditDTO>> getCreditImmobilierByTypeBien(@PathVariable String typeBien) {
        return ResponseEntity.ok(creditService.getCreditImmobilierByTypeBien(typeBien));
    }

    @GetMapping("/professionnel/raisonSociale/{raisonSociale}")
    @Operation(summary = "Récupérer les crédits professionnels par raison sociale", description = "Retourne tous les crédits professionnels pour une raison sociale spécifique")
    public ResponseEntity<List<CreditDTO>> getCreditProfessionnelByRaisonSociale(@PathVariable String raisonSociale) {
        return ResponseEntity.ok(creditService.getCreditProfessionnelByRaisonSociale(raisonSociale));
    }
}