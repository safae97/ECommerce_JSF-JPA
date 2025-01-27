//package ma.fstt.persistence;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class Test {
//
//    public static void main(String[] args) {
//
//        Internaute et = new Internaute();
//        et.setPassword("wbckhwbsdjh");
//        et.setUsername("nchbvsidvc");
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//
//        System.out.println("COMIITING");
//        em.persist(et);
//        em.getTransaction().commit();
//
//    }
//}
