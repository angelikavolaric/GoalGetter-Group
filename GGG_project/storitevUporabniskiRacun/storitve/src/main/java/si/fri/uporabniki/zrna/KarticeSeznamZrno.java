package si.fri.uporabniki.zrna;

import si.fri.kartice.entitete.Kartica;
//import com.kumuluz.ee.beans.QueryParameters;
import si.fri.kartice.entitete.KarticeSeznam;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped //dokler teče aplikacija
public class KarticeSeznamZrno {

    private Logger log = Logger.getLogger(KarticeSeznamZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Inicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());

        // inicializacija virov
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KarticeSeznamZrno.class.getSimpleName());

        // zapiranje virov
    }

    public List<Kartica> pridobiKarticeSeznam() {

        // TODO: missing implementation

        return null;

    }

/*    public List<Kartica> pridobiKarticeSeznam(QueryParameters query) {

        // TODO: missing implementation

        return null;

    }

    public Long pridobiKarticeSeznamCount(QueryParameters query) {

        // TODO: missing implementation

        return null;
    }*/

    public KarticeSeznam pridobiKarticeSeznam(int karticeSeznamId) {

        // TODO: missing implementation

        return null;

    }

    @Transactional
    public KarticeSeznam dodajKarticeSeznam(KarticeSeznam karticeSeznam) {

        // TODO: missing implementation

        return null;

    }

    @Transactional
    public void posodobiKarticeSeznam(int karticeSeznamId, KarticeSeznam karticeSeznam) {

        // TODO: missing implementation

    }

    @Transactional
    public boolean odstraniKarticeSeznam(int KarticeSeznamId) {

        // TODO: missing implementation

        return false;

    }
}
