package ma.fstt.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import ma.fstt.persistence.Internaute;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
@Data
public class InternauteBean implements Serializable {

    private String username;
    private String password;
    private String role; // Add role field to bind with the registration form
    private Internaute internaute;

    private EntityManagerFactory emf;
    private EntityManager em;

    public InternauteBean() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    // Registration method
    public String register() {
        Internaute newUser = new Internaute();
        newUser.setUsername(username);
        newUser.setPassword(password); // Store the password securely (e.g., hash it)

        // Set default role if none is provided
        if (role == null || role.isEmpty()) {
            newUser.setRole("client");  // Default role is "client"
        } else {
            newUser.setRole(role);  // Use role from user input if available
        }

        em.getTransaction().begin();
        em.persist(newUser);
        em.getTransaction().commit();

        return "login.xhtml?faces-redirect=true"; // Redirect to login page after registration
    }

    // Retrieve all clients
    public List<Internaute> getAllClients() {
        return em.createQuery("SELECT i FROM Internaute i", Internaute.class).getResultList();
    }

    // Login method
    public String login() {
        try {
            Internaute existingUser = em.createQuery(
                            "SELECT i FROM Internaute i WHERE i.username = :username AND i.password = :password",
                            Internaute.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            if (existingUser != null) {
                this.internaute = existingUser;

                // Store the logged-in internaute in the session
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInternaute", existingUser);

                // Redirect based on role
                if ("employee".equals(existingUser.getRole())) {
                    return "home.xhtml?faces-redirect=true"; // Redirect for employees
                } else {
                    return "home.xhtml?faces-redirect=true"; // Redirect for clients
                }
            } else {
                return "login.xhtml?faces-redirect=true"; // Redirect back to login on failure
            }
        } catch (Exception e) {
            return "login.xhtml?faces-redirect=true"; // Handle invalid login attempts
        }
    }
    public List<Internaute> getClients() {
        return em.createQuery("SELECT i FROM Internaute i WHERE i.role = 'client'", Internaute.class).getResultList();
    }

    // Delete a client
    public void deleteClient(Long clientId) {
        em.getTransaction().begin();
        try {
            Internaute clientToDelete = em.find(Internaute.class, clientId);
            if (clientToDelete != null) {
                // Check if the client has associated LignePanier entities
                boolean hasLignePaniers = em.createQuery(
                                "SELECT COUNT(lp) FROM LignePanier lp WHERE lp.panier.internaute.id = :clientId", Long.class)
                        .setParameter("clientId", clientId)
                        .getSingleResult() > 0;

                if (hasLignePaniers) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN,
                            "Cannot delete row, client has associated commands .",
                            ""));
                } else {
                    em.remove(clientToDelete);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Success",
                            "Client deleted successfully."));
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "An error occurred while attempting to delete the client."));
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Invalidate the session
        return "login.xhtml?faces-redirect=true"; // Redirect to the login page
    }

}
