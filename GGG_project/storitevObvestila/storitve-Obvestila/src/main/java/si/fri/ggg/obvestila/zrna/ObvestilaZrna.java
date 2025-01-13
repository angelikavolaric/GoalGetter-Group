package si.fri.ggg.obvestila.zrna;

import si.fri.ggg.obvestila.entitete.ObvestilaEnt;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

public class ObvestilaZrna {

    private Logger log = Logger.getLogger(ObvestilaZrna.class.getName());

    private EntityManagerFactory emf;
    private EntityManager em;

    // Constructor to initialize the EntityManager
    public ObvestilaZrna() {
        emf = Persistence.createEntityManagerFactory("storitevobvestila-jpa");
        em = emf.createEntityManager();
    }

    // Initialization method for logging and checking the EntityManager
    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + ObvestilaZrna.class.getSimpleName());
        if (em == null) {
            log.severe("EntityManager is null! Injection failed.");
        } else {
            log.info("EntityManager injected successfully.");
        }
    }

    // Destruction method for cleanup
    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + ObvestilaZrna.class.getSimpleName());
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    // Method to persist an ObvestilaEnt object
    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ObvestilaEnt ustvariObvestilo(ObvestilaEnt obvestiloEnt) {
        try {
            em.getTransaction().begin();
            em.persist(obvestiloEnt);
            em.flush();
            em.getTransaction().commit();
            log.info("Obvestilo persisted with ID: " + obvestiloEnt.getId());
        } catch (Exception e) {
            log.severe("Error persisting obvestilo: " + e.getMessage());
            return null;
        }
        return obvestiloEnt;
    }

    // Method to delete an ObvestilaEnt object by its ID
    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean odstraniObvestilo(int obvestiloId) {
        ObvestilaEnt obvestiloEnt = em.find(ObvestilaEnt.class, obvestiloId);
        if (obvestiloEnt != null) {
            em.getTransaction().begin();
            em.remove(obvestiloEnt);
            em.flush();
            em.getTransaction().commit();
            log.info("Obvestilo with ID: " + obvestiloId + " removed.");
            return true;
        }
        log.warning("Obvestilo with ID " + obvestiloId + " not found.");
        return false;
    }

    // Method to get an ObvestilaEnt object by its ID
    @Transactional
    public ObvestilaEnt pridobiObvestilo(int obvestiloId) {
        ObvestilaEnt obvestiloEnt = em.find(ObvestilaEnt.class, obvestiloId);
        if (obvestiloEnt == null) {
            throw new IllegalArgumentException("Obvestilo with ID " + obvestiloId + " not found.");
        }
        return obvestiloEnt;
    }

    // Method to retrieve all ObvestilaEnt objects
    @Transactional
    public List<ObvestilaEnt> pridobiVseObvestila() {
        if (em == null) {
            log.severe("EntityManager is null!");
        }
        List<ObvestilaEnt> seznam = em.createQuery("SELECT o FROM ObvestilaEnt o", ObvestilaEnt.class).getResultList();
        return seznam;
    }
}
