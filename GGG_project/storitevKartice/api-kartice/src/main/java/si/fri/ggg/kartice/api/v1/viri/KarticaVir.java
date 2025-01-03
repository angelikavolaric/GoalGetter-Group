package si.fri.ggg.kartice.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

//import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.ggg.kartice.dtos.KarticaDto;
import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;
import si.fri.ggg.kartice.zrna.UpravljanjeKarticSeznamZrno;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
//@ApplicationScoped
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/kartice")
public class KarticaVir {

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam kartic.", summary = "Seznam karic")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam kartic",
                    content = @Content(schema = @Schema(implementation = Kartica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih kartic")}
            )})

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kartica> getKartice(){
        KarticaZrno karticaZrno = new KarticaZrno();
        return karticaZrno.pridobiVseKartice();
        //return "{\"sporocilo\" : \"Pridobi kartice\"}";

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Kartica postKartica(Kartica novaKartica){
        KarticaZrno karticaZrno = new KarticaZrno();
        KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
        KarticeSeznam seznam = karticeSeznamZrno.pridobiKarticeSeznam(1);
        System.out.println("seznam V dodajanje " + seznam);
        return karticaZrno.dodajKartico(novaKartica, seznam);
    }

    @GET
    @Path("/{karticaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Kartica getKartica(@PathParam("karticaId") int karticaId) {
        KarticaZrno karticaZrno = new KarticaZrno();
        return karticaZrno.pridobiKartico(karticaId);
    }

    @PUT
    @Path("/{karticaId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Kartica putKartica(@PathParam("karticaId") int karticaId, Kartica novaKartica){
        KarticaZrno karticaZrno = new KarticaZrno();
        return karticaZrno.urediKartico(karticaId, novaKartica);
    }

    @DELETE
    @Path("/{karticaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteKartica(@PathParam("karticaId") int karticaId) {
        KarticaZrno karticaZrno = new KarticaZrno();
        boolean result = karticaZrno.izbrišiKartico(karticaId);
    }



}
