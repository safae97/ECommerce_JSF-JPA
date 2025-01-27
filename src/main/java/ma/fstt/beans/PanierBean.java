// File: ma/fstt/beans/PanierBean.java
package ma.fstt.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.Data;
import ma.fstt.persistence.Internaute;
import ma.fstt.persistence.LignePanier;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped  // Maintain state across multiple actions on the same page
@Data
public class PanierBean implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em;
    private List<LignePanier> lignePanierList;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        loadPanierItems();
    }

    public void loadPanierItems() {
        Internaute internaute = (Internaute) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("loggedInternaute");

        if (internaute != null) {
            TypedQuery<LignePanier> query = em.createQuery(
                    "SELECT lp FROM LignePanier lp WHERE lp.panier.internaute.id = :internauteId", LignePanier.class);
            query.setParameter("internauteId", internaute.getId());
            lignePanierList = query.getResultList();
        }
    }

    // Method to update quantity
    public void updateItemQuantity(LignePanier item) {
        em.getTransaction().begin();
        LignePanier dbItem = em.find(LignePanier.class, item.getId());
        if (dbItem != null) {
            dbItem.setQuantite(item.getQuantite());  // Update quantity in the database
        }
        em.getTransaction().commit();
        loadPanierItems();  // Refresh the list
    }

    // Method to delete an item
    public void deleteItem(LignePanier item) {
        em.getTransaction().begin();
        LignePanier dbItem = em.find(LignePanier.class, item.getId());
        if (dbItem != null) {
            em.remove(dbItem);  // Delete from the database
        }
        em.getTransaction().commit();
        loadPanierItems();  // Refresh the list after deletion
    }
}
