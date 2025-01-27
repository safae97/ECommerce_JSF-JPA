// File: ma/fstt/beans/LignePanierBean.java

package ma.fstt.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.Data;
import ma.fstt.persistence.Internaute;
import ma.fstt.persistence.LignePanier;
import ma.fstt.persistence.Panier;
import ma.fstt.persistence.Produit;

import java.util.List;

@Named
@RequestScoped
@Data
public class LignePanierBean {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em = emf.createEntityManager();

    private Long produitId; // Selected product ID
    private Integer quantite; // Quantity
    private Panier panier;
    private List<LignePanier> lignePanierList;

    public LignePanierBean() {
        loadLignePaniers();  // Load orders on initialization
    }
    public void addProductToPanier() {
        // Retrieve the logged-in internaute
        Internaute internaute = (Internaute) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("loggedInternaute");

        if (internaute == null) {
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                    .handleNavigation(FacesContext.getCurrentInstance(), null, "login.xhtml");
            return;
        }

        // Retrieve or create the user's panier
        TypedQuery<Panier> query = em.createQuery("SELECT p FROM Panier p WHERE p.internaute.id = :id", Panier.class);
        query.setParameter("id", internaute.getId());
        try {
            panier = query.getSingleResult();
        } catch (NoResultException e) {
            panier = new Panier();
            panier.setInternaute(internaute);
            em.getTransaction().begin();
            em.persist(panier);
            em.getTransaction().commit();
        }

        // Add product to panier
        Produit produit = em.find(Produit.class, produitId);
        LignePanier lignePanier = new LignePanier();
        lignePanier.setPanier(panier);
        lignePanier.setProduit(produit);
        lignePanier.setQuantite(quantite);

        em.getTransaction().begin();
        em.persist(lignePanier);
        em.getTransaction().commit();

        // Redirect to ListPanier.xhtml
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
                .handleNavigation(FacesContext.getCurrentInstance(), null, "ListPanier.xhtml");
    }



    public void loadLignePaniers() {
        lignePanierList = em.createQuery("SELECT lp FROM LignePanier lp", LignePanier.class).getResultList();
    }

    // Update the status of a LignePanier
    public void updateStatus(Long lignePanierId, LignePanier.Status status) {
        em.getTransaction().begin();
        LignePanier lp = em.find(LignePanier.class, lignePanierId);
        if (lp != null) {
            lp.setStatus(status);  // Update the status
            em.merge(lp);
        }
        em.getTransaction().commit();
        loadLignePaniers();  // Refresh the list
    }
    public LignePanier.Status[] getStatusValues() {
        return LignePanier.Status.values();
    }
}
