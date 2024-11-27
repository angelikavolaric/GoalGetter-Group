package si.fri.uporabniki.zrna;

import si.fri.uporabniki.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.logging.Logger;

public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + UporabnikZrno.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UporabnikZrno.class.getSimpleName());

        // zapiranje virov
    }

   // @PersistenceContext(unitName = "nakupovalni-seznami-jpa")
    //private EntityManager em;

    @Transactional
    public Uporabnik dodajUporabnika(Uporabnik uporabnik) {

        // TODO: missing implementation


        return null;

    }

    @Transactional
    public Uporabnik izbrišiUporabnika(int porabnikId) {

        // TODO: missing implementation


        return null;

    }

    @Transactional
    public Uporabnik urediUporabnika(int uporabnikId) {

        // TODO: missing implementation


        return null;

    }
}
