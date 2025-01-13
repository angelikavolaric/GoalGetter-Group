package si.fri.ggg.deljenje.zrna;




import si.fri.ggg.deljenje.entitete.Cilj;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
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
            return c;
        } catch (Exception e) {
            log.severe("Error adding cilj" + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error adding user", e);
        }
    }

    @Transactional
    public boolean odstraniCilj(int ciljId){
        try{
            Cilj cilj = em.find(Cilj.class, ciljId);
            if(cilj != null){
                em.getTransaction().begin();
                em.remove(ciljId);
                em.flush();
                em.getTransaction().commit();
                log.info("Cilj with ID: " + ciljId + " removed.");
                return true;
            } else {
                log.warning("User with ID " + ciljId + " not found.");
                return false;
            }
        }catch (Exception e) {
        log.severe("Error removing user: " + e.getMessage());
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        throw new RuntimeException("Error removing user with ID " + ciljId, e);
    }
    }

    @Transactional
    public Cilj urediCilj(int ciljId, Cilj cilj){
        Cilj obstojeciCilj = em.find(Cilj.class, ciljId);
        if(obstojeciCilj != null){
            obstojeciCilj.setCiljUr(cilj.getCiljUr());
            obstojeciCilj.setCiljMin(cilj.getCiljMin());
            obstojeciCilj.setOpis(cilj.getOpis());
            obstojeciCilj.setUporabnikid(cilj.getId());
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

    @Transactional
    public Cilj pridobiCiljeUp(int cID){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        //List<Cilj> cilj = em.createQuery("SELECT c FROM Cilj c", Cilj.class).getResultList();

        TypedQuery<Cilj> cilj = em.createQuery("SELECT c FROM Cilj c WHERE c.uporabnikid = :uporabnikId", Cilj.class);
        cilj.setParameter("uporabnikId", cID);
        try {
            Cilj u = cilj.getSingleResult();
            return u;
        } catch (NoResultException e) {
            log.warning("No result found for uporabnikId: " + cID);
            return null;
        }

    }

    /*@Transactional
    public Cilj pridobiCiljUp(int ciljId){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        List<Cilj> cilj = em.createQuery("SELECT c FROM Cilj c", Cilj.class).setMaxResults(1).getResultList();

        Cilj cilj = em.find(Cilj.class, ciljId);
        if(cilj == null){
            throw new IllegalArgumentException("Cilj with ID " + ciljId + " not found.");
        }
        return cilj;
    }*/
}
