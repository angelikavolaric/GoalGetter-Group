package si.fri.ggg.belezenje.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;
import si.fri.ggg.belezenje.zrna.UpravljanjeSeznamovUr;
import si.fri.ggg.belezenje.zrna.UreSeznamZrna;
import si.fri.ggg.belezenje.zrna.UreZrno;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import javax.inject.Inject;


@SuppressWarnings("SpellCheckingInspection")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/seznamiUr")
public class BelezenjeSeznamViri {

    //private final UreSeznamZrna ureSeznamZrna;
    private final UpravljanjeSeznamovUr upravljanjeSeznamovUrZrno;


    @Context
    protected UriInfo uriInfo;



    @javax.inject.Inject
    public BelezenjeSeznamViri(/*UreSeznamZrna ureSeznamZrna, */UpravljanjeSeznamovUr upravljanjeSeznamovUr){
        //this.ureSeznamZrna = ureSeznamZrna;
        this.upravljanjeSeznamovUrZrno = upravljanjeSeznamovUr;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UreSeznam> getUreSeznam() {
        UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
        return ureSeznamZrna.pridobiVseUreSeznamu();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UreSeznam postUreSeznam(UreSeznam novSeznamUr) {
        return upravljanjeSeznamovUrZrno.ustvariSeznamUr(novSeznamUr);
    }

    @GET
    @Path("/{ureSeznamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UreSeznam getUreSeznam(@PathParam("ureSeznamId") int ureSeznamId) {
        UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
        return ureSeznamZrna.pridobiUreSeznam(ureSeznamId);
    }

    @PUT
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UreSeznam putUreSeznam(@PathParam("ureSeznamId") int ureSeznamId, UreSeznam novSeznamUr) {
        UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
        return ureSeznamZrna.posodobiSeznamUr(ureSeznamId, novSeznamUr);
    }


    @DELETE
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUreSeznam(@PathParam("ureSeznamId") int ureSeznamId) {
        UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
        /*boolean result = */ureSeznamZrna.odstraniSeznamUr(ureSeznamId);
    }

    @POST
    @Path("/{ureSeznamId}/ure")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ure postUra (@PathParam("ureSeznamId")Integer ureSeznamId, Ure novaUra){
        UreZrno ureZrno = new UreZrno();
        UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
        UreSeznam seznam = ureSeznamZrna.pridobiUreSeznam(ureSeznamId);
        System.out.println("Seznam V dodajanje " + seznam);
        return ureZrno.dodajUro(novaUra, seznam);
    }

    @GET
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Ure> getUraSeznamUr(@PathParam("ureSeznamId") int ureSeznamId){
        UpravljanjeSeznamovUr upravljanjeSeznamovUr = new UpravljanjeSeznamovUr();
        return upravljanjeSeznamovUr.pridobiUreVSeznamu(ureSeznamId);
    }


    @PUT
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public UreSeznam putUraSeznam(@PathParam("ureSeznamId")Integer ureSeznamId, UreSeznam novSeznam){
        UpravljanjeSeznamovUr upravljanjeSeznamovUr = new UpravljanjeSeznamovUr();
        return upravljanjeSeznamovUr.ustvariSeznamUr(novSeznam);
    }

}
