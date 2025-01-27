package ma.fstt.persistence;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ligne_panier")
@Data
public class LignePanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id", nullable = false)
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.IN_PROGRESS;  // Default status

    public enum Status {
        IN_PROGRESS,
        DECLINED,
        ACCEPTED
    }
}
