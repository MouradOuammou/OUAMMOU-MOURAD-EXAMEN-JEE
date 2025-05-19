package ouammou.backend.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private TypeRemboursement type;

    @ManyToOne
    private Credit credit;

    public enum TypeRemboursement {
        MENSUALITE, REMBOURSEMENT_ANTICIPE
    }
}