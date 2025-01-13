package si.fri.ggg.belezenje.zrna;


//import com.kumuluz.ee.rest.client.RestClient;
//import com.kumuluz.ee.rest.client.RestClientResponse;
import si.fri.ggg.belezenje.dtos.UporabnikDto;
import si.fri.ggg.belezenje.dtos.UreDto;
import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UreZrno {

    private Logger log = Logger.getLogger(UreZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UreZrno.class.getSimpleName());
        if (em == null) {
            log.severe("EntityManager is null! Injection failed.");
        } else {
            log.info("EntityManager injected successfully.");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UreZrno.class.getSimpleName());
    }

    private EntityManagerFactory emf;
    private EntityManager em;

    public UreZrno() {
        try {
            emf = Persistence.createEntityManagerFactory("storitevbelezenje-jpa");
            em = emf.createEntityManager();
        } catch (Exception e) {
            log.severe("Error initializing EntityManager: " + e.getMessage());
            throw new RuntimeException("Error initializing EntityManager", e);
        }
    }

    @Transactional
    public Ure dodajUro(Ure novaUra, UreSeznam seznam) {
        try {
            if (seznam == null) {
                throw new IllegalArgumentException("UreSeznam not found.");
            }
            novaUra.setUreSeznam(seznam);
            seznam.getUre().add(novaUra);

            em.getTransaction().begin();
            em.persist(novaUra);
            em.merge(seznam);
            em.flush();
            em.getTransaction().commit();
            return novaUra;
        } catch (Exception e) {
            log.severe("Error adding Ure: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error adding Ure", e);
        }
    }

    @Transactional
    public boolean odstraniUro(int ureId) {
        try {
            Ure ure = em.find(Ure.class, ureId);
            if (ure != null) {
                em.getTransaction().begin();
                em.remove(ure);
                em.flush();
                em.getTransaction().commit();
                log.info("Ure with ID: " + ureId + " removed.");
                return true;
            } else {
                log.warning("Ure with ID " + ureId + " not found.");
                return false;
            }
        } catch (Exception e) {
            log.severe("Error removing Ure with ID " + ureId + ": " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error removing Ure with ID " + ureId, e);
        }
    }

    @Transactional
    public Ure urediUro(int uraId, Ure ure) {
        try {
            Ure obstojecaUra = em.find(Ure.class, uraId);
            if (obstojecaUra != null) {
                obstojecaUra.setVnosiUr(ure.getVnosiUr());
                obstojecaUra.setVnosiMin(ure.getVnosiMin());
                em.getTransaction().begin();
                Ure novaUra = em.merge(obstojecaUra);
                em.flush();
                em.getTransaction().commit();
                log.info("UreSeznam with ID: " + uraId + " updated.");
                return novaUra;
            } else {
                log.warning("Ura with ID " + uraId + " not found.");
                throw new IllegalArgumentException("Ura with ID: " + uraId + " not found.");
            }
        } catch (Exception e) {
            log.severe("Error updating Ure with ID " + uraId + ": " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error updating Ure with ID " + uraId, e);
        }
    }

    @Transactional
    public Ure pridobiUro(int uraId) {
        try {
            Ure ure = em.find(Ure.class, uraId);
            if (ure == null) {
                log.warning("Ura with ID " + uraId + " not found.");
                throw new IllegalArgumentException("Ura with ID " + uraId + " not found.");
            }
            return ure;
        } catch (Exception e) {
            log.severe("Error retrieving Ure with ID " + uraId + ": " + e.getMessage());
            throw new RuntimeException("Error retrieving Ure with ID " + uraId, e);
        }
    }

    @Transactional
    public List<Ure> pridobiVseUre() {
        try {
            if (em == null) {
                log.severe("EntityManager is null!");
                throw new RuntimeException("EntityManager is null!");
            }
            List<Ure> ure = em.createQuery("SELECT u FROM Ure u", Ure.class).getResultList();
            return ure;
        } catch (Exception e) {
            log.severe("Error retrieving all Ure: " + e.getMessage());
            throw new RuntimeException("Error retrieving all Ure", e);
        }
    }

    @Transactional
    public List<Object[]> pridobiUreUp(int uporabnikId) {
        try {
            if (em == null) {
                log.severe("EntityManager is null!");
                throw new RuntimeException("EntityManager is null!");
            }
            TypedQuery<Object[]> ure = em.createQuery("SELECT SUM(u.vnosiUr), SUM(u.vnosiMin) FROM Ure u WHERE u.uporabnikId = :uporabnikId", Object[].class);
            ure.setParameter("uporabnikId", uporabnikId);
            List<Object[]> u = ure.getResultList();
            return u;
        } catch (Exception e) {
            log.severe("Error retrieving all Ure: " + e.getMessage());
            throw new RuntimeException("Error retrieving all Ure", e);
        }
    }
}

/*package si.fri.ggg.belezenje.zrna;

import si.fri.ggg.belezenje.dtos.UreDto;
import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
public class UreZrno {



    private Logger log = Logger.getLogger(UreZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UreZrno.class.getSimpleName());
        if(em == null){
            log.severe("EntityManager is null! Injection failed.");
        }else{
            log.info("EntityManager injected successfully.");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UreZrno.class.getSimpleName());
    }


    private EntityManagerFactory emf;
    private EntityManager em;

    public UreZrno() {
        emf = Persistence.createEntityManagerFactory("storitevbelezenje-jpa");
        em = emf.createEntityManager();
    }

    /*@PersistenceContext
    private EntityManager em;*/
/*
    @Transactional
    public Ure dodajUro(Ure novaUra, UreSeznam seznam) {
        if(seznam == null){
            throw new IllegalArgumentException("UreSeznam not found.");
        }
        novaUra.setUreSeznam(seznam);
        seznam.getUre().add(novaUra);

        em.getTransaction().begin();
        em.persist(novaUra);
        em.merge(seznam);
        em.flush();
        em.getTransaction().commit();
        return novaUra;
    }

    @Transactional
    public boolean odstraniUro(int ureId){
        Ure ure = em.find(Ure.class, ureId);
        if(ure != null){
            em.getTransaction().begin();
            em.remove(ureId);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam with ID: " + ureId + " removed.");
            return true;
        }
        return false;
    }

    @Transactional
    public Ure urediUro(int uraId, Ure ure){
        Ure obstojecaUra = em.find(Ure.class, uraId);
        if(obstojecaUra != null){
            obstojecaUra.setVnosiUr(ure.getVnosiUr());
            obstojecaUra.setVnosiMin(ure.getVnosiMin());
            em.getTransaction().begin();
            Ure novaUra = em.merge(obstojecaUra);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam with ID: " + uraId + " updated.");
            return novaUra;
        }
        throw new IllegalArgumentException("Ura with ID: " + uraId + " not found.");
    }


    @Transactional
    public Ure pridobiUro(int uraId){
        Ure ure = em.find(Ure.class, uraId);
        if(ure == null){
            throw new IllegalArgumentException("Kartica with ID " + uraId + " not found.");
        }
        return ure;
    }

    @Transactional
    public List<Ure> pridobiVseUre(){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        List<Ure> ure = em.createQuery("SELECT u FROM Ure u", Ure.class).getResultList();
        return ure;
    }



    /*private final String uporabnikStoritevUrl = "http://localhost:8080/v1/uporabniki/";
    @Inject
    private RestClient restClient; // Inject RestClient to make HTTP requests

    public UporabnikDto getUporabnikById(Integer uporabnikId) {
        // Build the URL for the API endpoint
        String url = uporabnikStoritevUrl + uporabnikId;

        // Make the GET request
        try {
            RestClientResponse response = restClient.get(url);
        }

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Deserialize response to Uporabnik object
            return response.readEntity(Uporabnik.class);
        } else {
            // Handle error, for example, throw exception or return null
            throw new RuntimeException("Failed to fetch user data from User service.");
        }
    }

    @Transactional
    public List<Ure> pridobiVseUreUporabnika(Integer userId){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        String jpql = "SELECT u FROM Ure u WHERE u.uporabnikId = :userId";
        TypedQuery<Ure> query = em.createQuery(jpql, Ure.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }*

}
*/