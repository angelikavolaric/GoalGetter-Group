package si.fri.ggg.uporabniki.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import si.fri.ggg.uporabniki.entitete.Uporabnik;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName());
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName());
        // cleanup resources if needed
    }

    // EntityManager initialization
    private EntityManagerFactory emf;
    private EntityManager em;

    public UpravljanjeUporabnikovZrno() {
        try {
            emf = Persistence.createEntityManagerFactory("storitevuporabniki-jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            log.severe("Error initializing EntityManager: " + e.getMessage());
            throw new RuntimeException("Error initializing EntityManager", e);
        }
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Uporabnik ustvariUporabnikiSeznam(Uporabnik uporabnikiSeznam) {
        try {
            em.getTransaction().begin();
            em.persist(uporabnikiSeznam);  // Persist the new Uporabnik entity
            em.flush();
            em.getTransaction().commit();
            log.info("UporabnikiSeznam persisted with ID: " + uporabnikiSeznam.getId());
        } catch (Exception e) {
            log.severe("Error persisting uporabnikiSeznam with ID: " + uporabnikiSeznam.getId() + " - " + e.getMessage());
            // Rollback the transaction if it is still active
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Rethrow the exception after logging the error
            throw new RuntimeException("Error persisting uporabnikiSeznam with ID: " + uporabnikiSeznam.getId(), e);
        }
        return uporabnikiSeznam;  // Return the persisted entity
    }
}

/*package si.fri.ggg.uporabniki.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;

import si.fri.ggg.uporabniki.entitete.Uporabnik;

import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName());
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName());
        // cleanup resources if needed
    }
    /*
        @PersistenceContext(unitName = "storitevuporabniki-jpa")
        private EntityManager em;
    */
/*    private EntityManager em;

    public UpravljanjeUporabnikovZrno() {
        // Initialize the EntityManagerFactory and EntityManager
        emf = Persistence.createEntityManagerFactory("storitevuporabniki-jpa");
        em = emf.createEntityManager();
    }
    /////
    /*@Transactional
    public List<Uporabnik> pridobiUporabnikiVSeznamu(int uporabnikId) {
        Uporabnik uporabnik= em.find(Uporabnik.class, uporabnikId);
        if (uporabnik != null) {
            return uporabnik.getUporabnik();  // Return the list of Uporabnik related to UporabnikiSeznam
        }
        return null;  // Return null if UporabnikiSeznam with given ID is not found
    }*/
/*
    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Uporabnik ustvariUporabnikiSeznam(Uporabnik uporabnikiSeznam) {
        try {
            em.getTransaction().begin();
            em.persist(uporabnikiSeznam);
            em.flush();
            em.getTransaction().commit();
            log.info("UporabnikiSeznam persisted with ID: " + uporabnikiSeznam.getId());
        } catch (Exception e) {
            log.severe("Error persisting uporabnikiSeznam" + e.getMessage());
        }
        return uporabnikiSeznam;       // Return the persisted entity

    }

}
*/