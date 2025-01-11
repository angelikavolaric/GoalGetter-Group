package si.fri.ggg.kartice.zrna;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
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
public class KarticeSeznamZrno {

    private static final Logger log = Logger.getLogger(KarticeSeznamZrno.class.getName());

    private EntityManagerFactory emf;
    private EntityManager em;

    public KarticeSeznamZrno() {
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
        log.info("Inicializacija zrna " + KarticaZrno.class.getSimpleName());
        if (em == null) {
            log.severe("EntityManager is null! Injection failed.");
        } else {
            log.info("EntityManager injected successfully.");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Error closing resources", e);
        }
    }

    @Transactional
    public KarticeSeznam pridobiKarticeSeznam(int karticeSeznamId) {
        try {
            KarticeSeznam seznam = em.find(KarticeSeznam.class, karticeSeznamId);
            if (seznam != null) {
                return seznam;  // Return the KarticeSeznam entity
            } else {
                log.warning("KarticeSeznam with ID " + karticeSeznamId + " not found.");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching KarticeSeznam with ID: " + karticeSeznamId, e);
        }
        return null;  // Return null if KarticeSeznam with given ID is not found
    }

    @Transactional
    public List<KarticeSeznam> pridobiVseKarticeSeznamu() {
        try {
            if (em == null) {
                log.severe("EntityManager is null!");
                return null;
            }

            em.getTransaction().begin();
            List<KarticeSeznam> karticeSeznami = em.createQuery("SELECT ks FROM KarticeSeznam ks", KarticeSeznam.class)
                    .getResultList();
            em.flush();
            em.getTransaction().commit();
            return karticeSeznami;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all KarticeSeznam", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback on error
            }
        }
        return null;
    }

    @Transactional
    public KarticeSeznam posodobiKarticeSeznam(int karticeSeznamId, KarticeSeznam karticeSeznam) {
        try {
            KarticeSeznam existingSeznam = em.find(KarticeSeznam.class, karticeSeznamId);
            if (existingSeznam != null) {
                existingSeznam.setPredmet(karticeSeznam.getPredmet());
                existingSeznam.setOpis(karticeSeznam.getOpis());

                em.getTransaction().begin();
                em.merge(existingSeznam);  // Merge the updated entity
                em.flush();
                em.getTransaction().commit();
                log.info("KarticeSeznam with ID: " + karticeSeznamId + " updated");
                return existingSeznam;
            } else {
                log.warning("KarticeSeznam with ID " + karticeSeznamId + " not found for update.");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating KarticeSeznam with ID: " + karticeSeznamId, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback on error
            }
        }
        return null;  // Return null if KarticeSeznam not found or update failed
    }

    @Transactional
    public boolean odstraniKarticeSeznam(int karticeSeznamId) {
        try {
            KarticeSeznam karticeSeznam = em.find(KarticeSeznam.class, karticeSeznamId);
            if (karticeSeznam != null) {
                em.getTransaction().begin();
                em.remove(karticeSeznam);  // Remove the KarticeSeznam entity
                em.flush();
                em.getTransaction().commit();
                log.info("KarticeSeznam with ID: " + karticeSeznamId + " removed");
                return true;  // Return true if deleted
            } else {
                log.warning("KarticeSeznam with ID " + karticeSeznamId + " not found for removal.");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting KarticeSeznam with ID: " + karticeSeznamId, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback on error
            }
        }
        return false;  // Return false if KarticeSeznam with given ID is not found or deletion failed
    }
}


/*package si.fri.ggg.kartice.zrna;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class KarticeSeznamZrno {

    private Logger log = Logger.getLogger(KarticeSeznamZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());
        // cleanup resources if needed
    }
    private EntityManagerFactory emf;
    private EntityManager em;
    //@PersistenceContext(unitName = "storitevkartice-jpa")
    //private EntityManager em;

    public KarticeSeznamZrno() {
        // Initialize the EntityManagerFactory and EntityManager
        emf = Persistence.createEntityManagerFactory("storitevkartice-jpa");
        em = emf.createEntityManager();
    }

    @Transactional
    public KarticeSeznam pridobiKarticeSeznam(int karticeSeznamId) {
        KarticeSeznam seznam = em.find(KarticeSeznam.class, karticeSeznamId);
        if (seznam != null) {
            return seznam;  // Return the KarticeSeznam entity
        }
        return null;  // Return null if KarticeSeznam with given ID is not found
    }

    @Transactional
    public List<KarticeSeznam> pridobiVseKarticeSeznamu() {
        if (em == null) {
            log.severe("EntityManager is null!");
        }
        // Fetching all KarticeSeznam entities from the database
        em.getTransaction().begin();
        List<KarticeSeznam> karticeSeznami = em.createQuery("SELECT ks FROM KarticeSeznam ks", KarticeSeznam.class)
                .getResultList();
        em.flush();
        em.getTransaction().commit();
        return karticeSeznami;  // Return the list of all KarticeSeznam entities
    }


    @Transactional
    public KarticeSeznam posodobiKarticeSeznam(int karticeSeznamId, KarticeSeznam karticeSeznam) {
        KarticeSeznam existingSeznam = em.find(KarticeSeznam.class, karticeSeznamId);
        if (existingSeznam != null) {
            // Update the existing KarticeSeznam entity
            existingSeznam.setPredmet(karticeSeznam.getPredmet());
            existingSeznam.setOpis(karticeSeznam.getOpis());
            em.getTransaction().begin();
            em.merge(existingSeznam);  // Merge the updated entity
            em.flush();
            em.getTransaction().commit();
            log.info("KarticeSeznam with ID: " + karticeSeznamId + " updated");
        }
        return existingSeznam;
    }

    @Transactional
    public boolean odstraniKarticeSeznam(int karticeSeznamId) {
        KarticeSeznam karticeSeznam = em.find(KarticeSeznam.class, karticeSeznamId);
        if (karticeSeznam != null) {
            em.getTransaction().begin();
            em.remove(karticeSeznam);  // Remove the KarticeSeznam entity
            em.flush();
            em.getTransaction().commit();
            log.info("KarticeSeznam with ID: " + karticeSeznam.getId() + " removed");
            return true;               // Return true if deleted
        }
        return false;  // Return false if KarticeSeznam with given ID is not found
    }
}


/*package si.fri.ggg.kartice.zrna;

import si.fri.ggg.kartice.entitete.Kartica;
//import com.kumuluz.ee.beans.QueryParameters;
import si.fri.ggg.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
//import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

//@ApplicationScoped //dokler teče aplikacija
public class KarticeSeznamZrno {

    private Logger log = Logger.getLogger(KarticeSeznamZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());

        // zapiranje virov
    }

    /*@PersistenceContext(unitName = "goalgettergroup-jpa") //TODO - insert dependency
    private EntityManager em;*

    public List<Kartica> pridobiKarticeSeznam() {

        // TODO: missing implementation

        return null;

    }
*/
/*    public List<Kartica> pridobiKarticeSeznam(QueryParameters query) {

        // TODO: missing implementation

        return null;

    }

    public Long pridobiKarticeSeznamCount(QueryParameters query) {

        // TODO: missing implementation

        return null;
    }*

    public KarticeSeznam pridobiKarticeSeznam(int karticeSeznamId) {

        // TODO: missing implementation

        return null;

    }

    @Transactional
    public KarticeSeznam dodajKarticeSeznam(KarticeSeznam karticeSeznam) {

        // TODO: missing implementation

        return null;

    }

    @Transactional
    public void posodobiKarticeSeznam(int karticeSeznamId, KarticeSeznam karticeSeznam) {

        // TODO: missing implementation

    }

    @Transactional
    public boolean odstraniKarticeSeznam(int KarticeSeznamId) {

        // TODO: missing implementation

        return false;

    }
}
*/