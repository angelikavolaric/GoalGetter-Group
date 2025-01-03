package si.fri.ggg.uporabniki.zrna;

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
    private EntityManagerFactory emf;
    private EntityManager em;

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
