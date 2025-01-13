package si.fri.ggg.obvestila.zrna;

import si.fri.ggg.timer.entitete.TimerEnt;

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


public class TimerZrna {

    private Logger log = Logger.getLogger(TimerZrna.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + TimerZrna.class.getSimpleName());
        if(em == null){
            log.severe("EntityManager is null! Injection failed.");
        }else{
            log.info("EntityManager injected successfully.");
        }
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + TimerZrna.class.getSimpleName());
    }


    private EntityManagerFactory emf;
    private EntityManager em;

    public TimerZrna() {
        emf = Persistence.createEntityManagerFactory("storitevtimer-jpa");
        em = emf.createEntityManager();
    }

    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TimerEnt ustvariTimer(TimerEnt eT){
        try {
            em.getTransaction().begin();
            em.persist(eT);
            em.flush();
            em.getTransaction().commit();
            log.info("Timer persisted with ID: " + eT.getId() );
        } catch (Exception e) {
            log.severe("Error persisting timer" + e.getMessage());
        }
        return eT;

    }

    @Transactional
    public boolean odstraniTimer(int timerId){
        TimerEnt timerEnt = em.find(TimerEnt.class, timerId);
        if(timerEnt != null){
            em.getTransaction().begin();
            em.remove(timerId);
            em.flush();
            em.getTransaction().commit();
            log.info("Timer with ID: " + timerId + " removed.");
            return true;
        }
        return false;
    }

    @Transactional
    public TimerEnt pridobiTimer(int timerId){
        TimerEnt timerEnt = em.find(TimerEnt.class, timerId);
        if(timerEnt == null){
            throw new IllegalArgumentException("Timer with ID " + timerId + " not found.");
        }
        return timerEnt;
    }

    @Transactional
    public List<TimerEnt> pridobiVseTimere(){
        if (em == null){
            log.severe("EntityManager is null!");
        }
        List<TimerEnt> sez = em.createQuery("SELECT c FROM TimerEnt c", TimerEnt.class).getResultList();
        return sez;
    }

}
