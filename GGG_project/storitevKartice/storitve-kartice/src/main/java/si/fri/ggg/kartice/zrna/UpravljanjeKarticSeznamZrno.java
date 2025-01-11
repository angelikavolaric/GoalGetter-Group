package si.fri.ggg.kartice.zrna;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKarticSeznamZrno {

    private static final Logger log = Logger.getLogger(UpravljanjeKarticSeznamZrno.class.getName());

    // Inject EntityManager using @PersistenceContext
    private EntityManagerFactory emf;
    private EntityManager em;

    public UpravljanjeKarticSeznamZrno() {
        // Initialize the EntityManagerFactory and EntityManager
        try {
            emf = Persistence.createEntityManagerFactory("storitevkartice-jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            log.severe("Error initializing EntityManager: " + e.getMessage());
            throw new RuntimeException("Error initializing EntityManager", e);
        }
    }
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeKarticSeznamZrno.class.getSimpleName());
        // Initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeKarticSeznamZrno.class.getSimpleName());
        // Cleanup resources if needed
    }

    // Method to fetch Kartice (cards) by KarticeSeznam (list of cards)
    @Transactional
    public List<Kartica> pridobiKarticeVSeznamu(int karticeSeznamId) {
        try {
            KarticeSeznam seznam = em.find(KarticeSeznam.class, karticeSeznamId);
            if (seznam != null) {
                return seznam.getKartice();  // Return the list of Kartica related to KarticeSeznam
            } else {
                log.warning("KarticeSeznam with ID " + karticeSeznamId + " not found.");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching KarticeSeznam with ID: " + karticeSeznamId, e);
            return null;  // Return null if error occurs
        }
    }

    // Method to create and persist a KarticeSeznam (card list)
    @Transactional
    public KarticeSeznam ustvariKarticeSeznam(KarticeSeznam karticeSeznam) {
        try {
            em.persist(karticeSeznam);  // Persist the KarticeSeznam entity
            log.info("KarticeSeznam persisted with ID: " + karticeSeznam.getId());
            return karticeSeznam;  // Return the persisted entity
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error persisting KarticeSeznam", e);
            return null;  // Return null if error occurs during persistence
        }
    }
}

/*package si.fri.ggg.kartice.zrna;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.persistence.EntityManager;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKarticSeznamZrno {

    private Logger log = Logger.getLogger(UpravljanjeKarticSeznamZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeKarticSeznamZrno.class.getSimpleName());
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeKarticSeznamZrno.class.getSimpleName());
        // cleanup resources if needed
    }

     //   @PersistenceContext(unitName = "storitevkartice-jpa")
    //    private EntityManager em;

    private EntityManagerFactory emf;
    private EntityManager em;

    public UpravljanjeKarticSeznamZrno() {
        // Initialize the EntityManagerFactory and EntityManager
        emf = Persistence.createEntityManagerFactory("storitevkartice-jpa");
        em = emf.createEntityManager();
    }
    /////
    @Transactional
    public List<Kartica> pridobiKarticeVSeznamu(int karticeSeznamId) {
        KarticeSeznam seznam = em.find(KarticeSeznam.class, karticeSeznamId);
        if (seznam != null) {
            return seznam.getKartice();  // Return the list of Kartica related to KarticeSeznam
        }
        return null;  // Return null if KarticeSeznam with given ID is not found
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public KarticeSeznam ustvariKarticeSeznam(KarticeSeznam karticeSeznam) {
        try {
            em.getTransaction().begin();
            em.persist(karticeSeznam);
            em.flush();
            em.getTransaction().commit();
            log.info("KarticeSeznam persisted with ID: " + karticeSeznam.getId());
        } catch (Exception e) {
            log.severe("Error persisting karticeSeznam" + e.getMessage());
        }
        return karticeSeznam;       // Return the persisted entity

    }

}
*/