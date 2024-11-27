package si.fri.kartice.zrna;

import si.fri.kartice.entitete.Kartica;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.logging.Logger;

public class KarticaZrno {

    private Logger log = Logger.getLogger(KarticaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KarticaZrno.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticaZrno.class.getSimpleName());

        // zapiranje virov
    }

   // @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    //private EntityManager em;

    @Transactional
    public Kartica dodajKartico(Kartica kartica) {

        // TODO: missing implementation


        return null;

    }

    @Transactional
    public Kartica izbrišiKartico(int karticaId) {

        // TODO: missing implementation


        return null;

    }

    @Transactional
    public Kartica urediKartico(int karticaId) {

        // TODO: missing implementation


        return null;

    }
}
