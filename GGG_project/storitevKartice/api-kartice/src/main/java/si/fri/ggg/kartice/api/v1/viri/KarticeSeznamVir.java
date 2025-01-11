package si.fri.ggg.kartice.api.v1.viri;

//import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.ggg.kartice.dtos.KarticeSeznamDto;
import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;
import si.fri.ggg.kartice.zrna.UpravljanjeKarticSeznamZrno;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Timestamp;
import java.util.List;


@SuppressWarnings("SpellCheckingInspection")
//@ApplicationScoped
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/seznamiKartic")
public class KarticeSeznamVir {


    private final KarticeSeznamZrno karticeSeznamZrno;
    private final UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno;
    @Context
    protected UriInfo uriInfo;

    @javax.inject.Inject
    public KarticeSeznamVir(KarticeSeznamZrno karticeSeznamZrno, UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno) {
        this.karticeSeznamZrno = karticeSeznamZrno;
        this.upravljanjeKarticSeznamZrno = upravljanjeKarticSeznamZrno;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<KarticeSeznam> getKarticeSeznam(){
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        return karticeSeznamZrno.pridobiVseKarticeSeznamu();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public KarticeSeznam postKarticeSeznam(KarticeSeznam novKarticeSeznam){
        return upravljanjeKarticSeznamZrno.ustvariKarticeSeznam(novKarticeSeznam);
    }

    @GET
    @Path("/{karticaSeznamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public KarticeSeznam getKarticaSeznam(@PathParam("karticaSeznamId") int karticaSeznamId) {
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        return karticeSeznamZrno.pridobiKarticeSeznam(karticaSeznamId);
    }

    @PUT
    @Path("/{karticaSeznamId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public KarticeSeznam putKarticaSeznam(@PathParam("karticaSeznamId") int karticaSeznamId, KarticeSeznam novKarticeSeznam) {
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        return karticeSeznamZrno.posodobiKarticeSeznam(karticaSeznamId, novKarticeSeznam);
    }

    @DELETE
    @Path("/{karticaSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteKarticeSeznam(@PathParam("karticaSeznamId") int karticaSeznamId) {
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        boolean result = karticeSeznamZrno.odstraniKarticeSeznam(karticaSeznamId);
        //log.info("Seznam kartic " + karticaSeznamId + "izbrisan = " + result);
    }

    @POST
    @Path("/{karticaSeznamId}/kartice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Kartica postKartica(@PathParam("karticaSeznamId")Integer karticeSeznamId,Kartica novaKartica){
        KarticaZrno karticaZrno = new KarticaZrno();
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        KarticeSeznam seznam = karticeSeznamZrno.pridobiKarticeSeznam(karticeSeznamId);
        System.out.println("seznam V dodajanje " + seznam);
        return karticaZrno.dodajKartico(novaKartica, seznam);
    }

    @GET
    @Path("/{karticaSeznamId}/kartice")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kartica> getKarticaSeznamKartice(@PathParam("karticaSeznamId") int karticaSeznamId) {
        UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno = new UpravljanjeKarticSeznamZrno();
        return upravljanjeKarticSeznamZrno.pridobiKarticeVSeznamu(karticaSeznamId);
    }



    @PUT
    @Path("/{karticaSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public KarticeSeznam putKarticaSeznam(@PathParam("karticaSeznamId")Integer karticeSeznamId, KarticeSeznam novSeznam) {
        UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno = new UpravljanjeKarticSeznamZrno();
        return upravljanjeKarticSeznamZrno.ustvariKarticeSeznam(novSeznam);
    }


}