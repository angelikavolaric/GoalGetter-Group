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

@ApplicationScoped

public class UreSeznamZrna {

    private Logger log = Logger.getLogger(UreSeznamZrna.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UreSeznamZrna.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UreSeznamZrna.class.getSimpleName());
    }

    private EntityManagerFactory emf;
    private EntityManager em;

    public UreSeznamZrna() {
        emf = Persistence.createEntityManagerFactory("storitevbelezenje-jpa");
        em = emf.createEntityManager();
    }

    @Transactional
    public UreSeznam pridobiUreSeznam(int ureSeznamId){
        UreSeznam seznam = em.find(UreSeznam.class, ureSeznamId);
        if(seznam != null) {
            return seznam;
        }
        return null;
    }

    @Transactional
    public List<UreSeznam> pridobiVseUreSeznamu(){
        if (em == null){
            log.severe("Entity manager is null!");
        }
        em.getTransaction().begin();
        List<UreSeznam> seznami = em.createQuery("SELECT us FROM UreSeznam us", UreSeznam.class).getResultList();
        em.flush();
        em.getTransaction().commit();
        return seznami;
    }

    @Transactional
    public UreSeznam posodobiSeznamUr(int ureSeznamId, UreSeznam seznam){
        UreSeznam obstojecSeznam = em.find(UreSeznam.class, ureSeznamId);

        if(obstojecSeznam !=  null) {
            obstojecSeznam.setOpis(seznam.getOpis());
            obstojecSeznam.setPredmet(seznam.getPredmet());
            em.getTransaction().begin();
            em.merge(obstojecSeznam);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam with ID: " + ureSeznamId + " updated");
        }
        return obstojecSeznam;
    }

    @Transactional
    public boolean odstraniSeznamUr(int ureSeznamId) {
        UreSeznam seznam = em.find(UreSeznam.class, ureSeznamId);

        if (seznam != null) {
            em.getTransaction().begin();
            em.remove(seznam);
            em.flush();
            em.getTransaction().commit();
            log.info("UreSeznam with ID: " + seznam.getId() + " removed");

            return true;
        }
        return false;
    }

}
