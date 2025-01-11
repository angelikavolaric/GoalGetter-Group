package si.fri.ggg.deljenje.zrna;




import si.fri.ggg.deljenje.entitete.Cilj;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class CiljZrno {



    private Logger log = Logger.getLogger(CiljZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + CiljZrno.class.getSimpleName());
        if(em == null){
            log.severe("EntityManager is null! Injection failed.");
        }else{
            log.info("EntityManager injected successfully.");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + CiljZrno.class.getSimpleName());
    }


    private EntityManagerFactory emf;
    private EntityManager em;

    public CiljZrno() {
        emf = Persistence.createEntityManagerFactory("storitevdeljenje-jpa");
        em = emf.createEntityManager();
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Cilj ustvariCilj(Cilj c){
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.flush();
            em.getTransaction().commit();
            log.info("Cilj persisted with ID: " + c.getId() );
        } catch (Exception e) {
            log.severe("Error persisting cilj" + e.getMessage());
        }
        return c;

    }

    @Transactional
    public boolean odstraniCilj(int ciljId){
        Cilj cilj = em.find(Cilj.class, ciljId);
        if(cilj != null){
            em.getTransaction().begin();
            em.remove(ciljId);
            em.flush();
            em.getTransaction().commit();
            log.info("Cilj with ID: " + ciljId + " removed.");
            return true;
        }
        return false;
    }

    @Transactional
    public Cilj urediCilj(int ciljId, Cilj cilj){
        Cilj obstojeciCilj = em.find(Cilj.class, ciljId);
        if(obstojeciCilj != null){
            obstojeciCilj.setCiljUr(cilj.getCiljUr());
            obstojeciCilj.setCiljMin(cilj.getCiljMin());
            em.getTransaction().begin();
            Cilj novCilj = em.merge(obstojeciCilj);
            em.flush();
            em.getTransaction().commit();
            log.info("Cilj with ID: " + ciljId + " updated.");
            return novCilj;
        }
        throw new IllegalArgumentException("Cilj with ID: " + ciljId + " not found.");
    }


    @Transactional
    public Cilj pridobiCilj(int ciljId){
        Cilj cilj = em.find(Cilj.class, ciljId);
        if(cilj == null){
            throw new IllegalArgumentException("Cilj with ID " + ciljId + " not found.");
        }
        return cilj;
    }

    @Transactional
    public List<Cilj> pridobiVseCilje(){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        List<Cilj> cilj = em.createQuery("SELECT c FROM Cilj c", Cilj.class).getResultList();
        return cilj;
    }
}
