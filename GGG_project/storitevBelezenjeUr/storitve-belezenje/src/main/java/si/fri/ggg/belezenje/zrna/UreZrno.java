package si.fri.ggg.belezenje.zrna;

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

}
