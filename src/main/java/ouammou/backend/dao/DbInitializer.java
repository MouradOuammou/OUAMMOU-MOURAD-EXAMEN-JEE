package ouammou.backend.dao;

import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ouammou.backend.entites.*;
import ouammou.backend.repositories.*;


import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final CreditPersonnelRepository creditPersonnelRepository;
    private final CreditImmobilierRepository creditImmobilierRepository;
    private final CreditProfessionnelRepository creditProfessionnelRepository;
    private final RemboursementRepository remboursementRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Créer des clients
        Client client1 = new Client();
        client1.setNom("Dupont Jean");
        client1.setEmail("jean.dupont@email.com");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setNom("Martin Sophie");
        client2.setEmail("sophie.martin@email.com");
        clientRepository.save(client2);

        // Créer des utilisateurs
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("1234"); // admin
        adminUser.setRole("ROLE_ADMIN");
        userRepository.save(adminUser);

        User employeUser = new User();
        employeUser.setUsername("employe");
        employeUser.setPassword("1234"); // employe
        employeUser.setRole("ROLE_EMPLOYE");
        userRepository.save(employeUser);

        User clientUser = new User();
        clientUser.setUsername("client");
        clientUser.setPassword("1234    "); // client
        clientUser.setRole("ROLE_CLIENT");
        clientUser.setClient(client1);
        userRepository.save(clientUser);

        // Créer des crédits personnels
        CreditPersonnel creditPersonnel = new CreditPersonnel();
        creditPersonnel.setClient(client1);
        creditPersonnel.setDateDemande(new Date());
        creditPersonnel.setStatut(Credit.StatutCredit.ACCEPTE);
        creditPersonnel.setDateAcception(new Date());
        creditPersonnel.setMontant(15000.0);
        creditPersonnel.setDuree(24);
        creditPersonnel.setTauxInteret(2.5);
        creditPersonnel.setMotif("Achat voiture");
        creditPersonnelRepository.save(creditPersonnel);

        // Créer des crédits immobiliers
        CreditImmobilier creditImmobilier = new CreditImmobilier();
        creditImmobilier.setClient(client1);
        creditImmobilier.setDateDemande(new Date());
        creditImmobilier.setStatut(Credit.StatutCredit.EN_COURS);
        creditImmobilier.setMontant(200000.0);
        creditImmobilier.setDuree(240);
        creditImmobilier.setTauxInteret(1.5);
        creditImmobilier.setTypeBien(CreditImmobilier.TypeBien.APPARTEMENT);
        creditImmobilierRepository.save(creditImmobilier);

        // Créer des crédits professionnels
        CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
        creditProfessionnel.setClient(client2);
        creditProfessionnel.setDateDemande(new Date());
        creditProfessionnel.setStatut(Credit.StatutCredit.ACCEPTE);
        creditProfessionnel.setDateAcception(new Date());
        creditProfessionnel.setMontant(50000.0);
        creditProfessionnel.setDuree(60);
        creditProfessionnel.setTauxInteret(3.0);
        creditProfessionnel.setMotif("Développement activité");
        creditProfessionnel.setRaisonSociale("Martin Consulting");
        creditProfessionnelRepository.save(creditProfessionnel);

        // Créer des remboursements
        Calendar calendar = Calendar.getInstance();

        for (int i = 1; i <= 3; i++) {
            Remboursement remboursement = new Remboursement();
            remboursement.setCredit(creditPersonnel);
            calendar.add(Calendar.MONTH, 1);
            remboursement.setDate(calendar.getTime());
            remboursement.setMontant(650.0);
            remboursement.setType(Remboursement.TypeRemboursement.MENSUALITE);
            remboursementRepository.save(remboursement);
        }

        Remboursement remboursementAnticipe = new Remboursement();
        remboursementAnticipe.setCredit(creditProfessionnel);
        remboursementAnticipe.setDate(new Date());
        remboursementAnticipe.setMontant(5000.0);
        remboursementAnticipe.setType(Remboursement.TypeRemboursement.REMBOURSEMENT_ANTICIPE);
        remboursementRepository.save(remboursementAnticipe);

        System.out.println("BD Fonctionne avec succès !");
    }
}
