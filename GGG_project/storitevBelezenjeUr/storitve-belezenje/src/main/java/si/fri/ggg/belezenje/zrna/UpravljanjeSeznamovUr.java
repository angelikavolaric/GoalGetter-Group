package si.fri.ggg.belezenje.zrna;

import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

@ApplicationScoped
public class UpravljanjeSeznamovUr {

    private Logger log = Logger.getLogger(UpravljanjeSeznamovUr.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeSeznamovUr.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeSeznamovUr.class.getSimpleName());
    }

    private EntityManagerFactory emf;
    private EntityManager em;

    public UpravljanjeSeznamovUr() {
        try {
            emf = Persistence.createEntityManagerFactory("storitevbelezenje-jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            log.severe("Error initializing EntityManager: " + e.getMessage());
            throw new RuntimeException("Error initializing EntityManager", e);
        }
    }

    @Transactional
    public List<Ure> pridobiUreVSeznamu(int ureSeznamId) {
        try {
            UreSeznam seznam = em.find(UreSeznam.class, ureSeznamId);
            if (seznam != null) {
                return seznam.getUre();
            } else {
                log.warning("UreSeznam with ID " + ureSeznamId + " not found.");
                return null; // Return null if the list does not exist
            }
        } catch (Exception e) {
            log.severe("Error retrieving UreSeznam with ID " + ureSeznamId + ": " + e.getMessage());
            throw new RuntimeException("Error retrieving UreSeznam with ID " + ureSeznamId, e);
        }
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UreSeznam ustvariSeznamUr(UreSeznam seznam) {
        try {
            em.getTransaction().begin();
            em.persist(seznam);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam persisted with ID: " + seznam.getId());
            return seznam; // Return the persisted UreSeznam entity
        } catch (Exception e) {
            log.severe("Error persisting seznam: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error persisting seznam", e);
        }
    }
}

/*package si.fri.ggg.belezenje.zrna;


import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;


import javax.persistence.*;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

@ApplicationScoped
public class UpravljanjeSeznamovUr {
    private Logger log = Logger.getLogger(UpravljanjeSeznamovUr.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UpravljanjeSeznamovUr.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeSeznamovUr.class.getSimpleName());
    }

    private EntityManagerFactory emf;
    private EntityManager em;

    public UpravljanjeSeznamovUr() {
        emf = Persistence.createEntityManagerFactory("storitevbelezenje-jpa");
        em = emf.createEntityManager();
    }

    @Transactional
    public List<Ure> pridobiUreVSeznamu(int ureSeznamId){
        UreSeznam seznam = em.find(UreSeznam.class, ureSeznamId);
        if(seznam != null) {
            return seznam.getUre();
        }
        return null;
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UreSeznam ustvariSeznamUr(UreSeznam seznam){
        try {
            em.getTransaction().begin();
            em.persist(seznam);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam persisted with ID: " + seznam.getId());
        } catch (Exception e) {
            log.severe("Error persisting seznam" + e.getMessage());
        }
        return seznam;

    }
}
*/