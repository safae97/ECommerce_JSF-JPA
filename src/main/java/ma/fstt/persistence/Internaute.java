package ma.fstt.persistence;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "internautes")
@Data
public class Internaute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role = "client"; // Either "employee" or "client"

    @OneToMany(mappedBy = "internaute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Panier> paniers;
}
