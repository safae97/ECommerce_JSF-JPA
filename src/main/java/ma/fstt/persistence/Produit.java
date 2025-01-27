package ma.fstt.persistence;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "produits")
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prix", nullable = false)
    private Double prix;

    // Remove direct reference to Panier and add association with LignePanier
    @OneToMany(mappedBy = "produit", orphanRemoval = true)
    private List<LignePanier> lignePaniers;

    // Pre-remove check to prevent deletion if linked
    @PreRemove
    public void checkIfLinkedToLignePanier() {
        if (lignePaniers != null && !lignePaniers.isEmpty()) {
            throw new IllegalStateException("Cannot delete product because it is referenced in orders.");
        }
    }
}
