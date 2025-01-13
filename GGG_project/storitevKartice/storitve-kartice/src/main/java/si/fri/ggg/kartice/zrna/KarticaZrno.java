package si.fri.ggg.kartice.zrna;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class KarticaZrno {

    private Logger log = Logger.getLogger(KarticaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KarticaZrno.class.getSimpleName());
        if (em == null) {
            log.severe("EntityManager is null! Injection failed.");
        } else {
            log.info("EntityManager injected successfully.");
        }
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticaZrno.class.getSimpleName());
        // cleanup resources if needed
    }

    private EntityManagerFactory emf;
    private EntityManager em;

    public KarticaZrno() {
        try {
            emf = Persistence.createEntityManagerFactory("storitevkartice-jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            log.severe("Error initializing EntityManager: " + e.getMessage());
            throw new RuntimeException("Error initializing EntityManager", e);
        }
    }

    @Transactional
    public Kartica dodajKartico(Kartica novaKartica, KarticeSeznam seznam) {
        if (seznam == null) {
            throw new IllegalArgumentException("KarticeSeznam not found.");
        }
        novaKartica.setKarticeSeznam(seznam);
        seznam.getKartice().add(novaKartica); // Ensure kartice list is updated
        em.getTransaction().begin();
        em.persist(novaKartica);
        em.merge(seznam);  // This can help ensure the KarticeSeznam entity is updated
        em.flush();
        em.getTransaction().commit();

        return novaKartica; // Return the persisted Kartica entity
    }



    @Transactional
    public boolean izbrišiKartico(int karticaId) {
        Kartica kartica = em.find(Kartica.class, karticaId);  // Find Kartica by ID
        if (kartica != null) {
            em.getTransaction().begin();
            em.remove(kartica);  // Remove the Kartica entity
            em.flush();
            em.getTransaction().commit();
            log.info("KarticeSeznam with ID : " + karticaId + " removed");
            return true;      // Return the deleted Kartica
        }
        return false;  // Return null if not found
    }

    @Transactional
    public Kartica urediKartico(int karticaId, Kartica kartica) {
        Kartica existingKartica = em.find(Kartica.class, karticaId);
        if (existingKartica != null) {
            // Update existing Kartica entity
            existingKartica.setVprasanje(kartica.getVprasanje());
            existingKartica.setOdgovor(kartica.getOdgovor());
            em.getTransaction().begin();
            Kartica posodKartica = em.merge(existingKartica);  // Merge the updated Kartica entity
            em.flush();
            em.getTransaction().commit();
            log.info("KarticeSeznam with ID : " + karticaId + " updated");
            return posodKartica;
        }
        throw new IllegalArgumentException("Kartica with ID " + karticaId + " not found.");
        //return null;  // Return null if Kartica with the given ID is not found
    }

    @Transactional
    public Kartica pridobiKartico(int karticaId) {
        Kartica kartica = em.find(Kartica.class, karticaId);  // Retrieve Kartica by ID
        if (kartica == null) {
            throw new IllegalArgumentException("Kartica with ID " + karticaId + " not found.");
        }
        return kartica;  // Return the found Kartica
    }

    @Transactional
    public List<Kartica> pridobiVseKartice() {
        if (em == null) {
            log.severe("EntityManager is null!");
        }
        List<Kartica> kartice = em.createQuery("SELECT k FROM Kartica k", Kartica.class)
                .getResultList();
        return kartice;  // Return the list of all Kartica

    }

}
