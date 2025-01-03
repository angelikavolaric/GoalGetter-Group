package si.fri.ggg.uporabniki.zrna;

import si.fri.ggg.uporabniki.entitete.Uporabnik;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());
        if (em == null) {
            log.severe("EntityManager is null! Injection failed.");
        } else {
            log.info("EntityManager injected successfully.");
        }
        // initialize resources if needed
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());
        // cleanup resources if needed
    }
////
    private EntityManagerFactory emf;
    private EntityManager em;

    public UporabnikZrno() {
        // Initialize the EntityManagerFactory and EntityManager
        emf = Persistence.createEntityManagerFactory("storitevuporabniki-jpa");
        em = emf.createEntityManager();
    }
    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik novaUporabnik) {
        em.getTransaction().begin();
        em.persist(novaUporabnik);
        em.flush();
        em.getTransaction().commit();
        return novaUporabnik; // Return the persisted Uporabnik entity
    }

    @Transactional
    public boolean izbrišiUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);  // Find Uporabnik by ID
        if (uporabnik != null) {
            em.getTransaction().begin();
            em.remove(uporabnik);  // Remove the Uporabnik entity
            em.flush();
            em.getTransaction().commit();
            log.info("UporabnikiSeznam with ID : " + uporabnikId + " removed");
            return true;      // Return the deleted Uporabnik
        }
        return false;  // Return null if not found
    }

    @Transactional
    public Uporabnik urediUporabnika(int uporabnikId, Uporabnik uporabnik) {
        Uporabnik existingUporabnik = em.find(Uporabnik.class, uporabnikId);
        if (existingUporabnik != null) {
            // Update existing Uporabnik entity
            existingUporabnik.setIme(uporabnik.getIme());
            existingUporabnik.setPriimek(uporabnik.getPriimek());
            existingUporabnik.setUporabniskoIme(uporabnik.getUporabniskoIme());
            existingUporabnik.setEmail(uporabnik.getEmail());
            em.getTransaction().begin();
            Uporabnik posodUporabnik = em.merge(existingUporabnik);  // Merge the updated Uporabnik entity
            em.flush();
            em.getTransaction().commit();
            log.info("UporabnikiSeznam with ID : " + uporabnikId + " updated");
            return posodUporabnik;
        }
        throw new IllegalArgumentException("Uporabnik with ID " + uporabnikId + " not found.");
        //return null;  // Return null if Uporabnik with the given ID is not found
    }

    @Transactional
    public Uporabnik pridobiUporabnika(int uporabnikId) {
        Uporabnik uporabnik = em.find(Uporabnik.class, uporabnikId);  // Retrieve Uporabnik by ID
        if (uporabnik == null) {
            throw new IllegalArgumentException("Uporabnik with ID " + uporabnikId + " not found.");
        }
        return uporabnik;  // Return the found Uporabnik
    }

    @Transactional
    public List<Uporabnik> pridobiVseUporabnike() {
        if (em == null) {
            log.severe("EntityManager is null!");
        }
        List<Uporabnik> uporabniki = em.createQuery("SELECT k FROM Uporabnik k", Uporabnik.class)
                .getResultList();
        return uporabniki;  // Return the list of all Uporabnik

    }

}
