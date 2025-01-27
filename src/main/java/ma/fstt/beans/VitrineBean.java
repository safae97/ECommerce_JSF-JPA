// File: ma/fstt/beans/VitrineBean.java
package ma.fstt.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.Data;
import ma.fstt.persistence.Produit;
import ma.fstt.persistence.LignePanier;

import java.util.List;

@Named
@RequestScoped
@Data
public class VitrineBean {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private EntityManager em = emf.createEntityManager();

    private Long id; // For editing existing products
    private String nom;
    private Double prix;
    private List<Produit> produits;

    public VitrineBean() {
        loadProduits(); // Load products on initialization
    }

    public void loadProduits() {
        produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
    }

    // Delete a product only if it's not linked to any LignePanier
    public String deleteProduct(Long productId) {
        Produit produit = em.find(Produit.class, productId);

        if (produit != null) {
            // Check if the product is linked to any LignePanier
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(lp) FROM LignePanier lp WHERE lp.produit.id = :productId", Long.class);
            query.setParameter("productId", productId);
            Long count = query.getSingleResult();

            if (count == 0) { // Only delete if not referenced
                em.getTransaction().begin();
                em.remove(produit);
                em.getTransaction().commit();
                loadProduits(); // Refresh the product list
                return "emp_list_products.xhtml?faces-redirect=true"; // Redirect to product list
            } else {
                // Handle the case where the product is still linked
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Cannot delete Selected Product is part of an existing order.",""));
            }
        }
        return null; // Stay on the same page if deletion is not allowed
    }
    public String createProduct() {
        Produit newProduit = new Produit();
        newProduit.setNom(nom);
        newProduit.setPrix(prix);

        em.getTransaction().begin();
        em.persist(newProduit);
        em.getTransaction().commit();

        loadProduits(); // Refresh the product list
        return "emp_list_products.xhtml?faces-redirect=true"; // Redirect to product list
    }


    // Update product details inline
    public String updateProductInline(Produit product) {
        em.getTransaction().begin();
        Produit dbProduct = em.find(Produit.class, product.getId());
        if (dbProduct != null) {
            dbProduct.setNom(product.getNom());
            dbProduct.setPrix(product.getPrix());
            em.merge(dbProduct);
        }
        em.getTransaction().commit();
        loadProduits();  // Refresh the product list
        return null;  // Stay on the same page
    }
}
