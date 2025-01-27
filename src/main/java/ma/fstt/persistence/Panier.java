package ma.fstt.persistence;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "paniers")
@Data
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Map association with LignePanier instead of directly with Produit
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignePaniers;

    @ManyToOne
    @JoinColumn(name = "internaute_id", nullable = false)
    private Internaute internaute;
}
