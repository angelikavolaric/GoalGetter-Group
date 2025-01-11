package si.fri.ggg.uporabniki.api.v1.viri;

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
import si.fri.ggg.uporabniki.entitete.Uporabnik;
import si.fri.ggg.uporabniki.zrna.UporabnikZrno;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
//@ApplicationScoped
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/uporabniki")
public class UporabnikVir {

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam uporabnike.", summary = "Seznam uporabnikov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")}
            )})

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uporabnik> getUporabniki(){
        UporabnikZrno uporabnikZrno = new UporabnikZrno();
        return uporabnikZrno.pridobiVseUporabnike();
        //return "{\"sporocilo\" : \"Pridobi uporabniki\"}";

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uporabnik postUporabnik(Uporabnik novUporabnik){
        UporabnikZrno uporabnikZrno = new UporabnikZrno();
        return uporabnikZrno.dodajUporabnika(novUporabnik);
    }

    @GET
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uporabnik getUporabnik(@PathParam("uporabnikId") int uporabnikId) {
        UporabnikZrno uporabnikZrno = new UporabnikZrno();
        return uporabnikZrno.pridobiUporabnika(uporabnikId);
    }

    @PUT
    @Path("/{uporabnikId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uporabnik putUporabnik(@PathParam("uporabnikId") int uporabnikId, Uporabnik novaUporabnik){
        UporabnikZrno uporabnikZrno = new UporabnikZrno();
        return uporabnikZrno.urediUporabnika(uporabnikId, novaUporabnik);
    }

    @DELETE
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUporabnik(@PathParam("uporabnikId") int uporabnikId) {
        UporabnikZrno uporabnikZrno = new UporabnikZrno();
        boolean result = uporabnikZrno.izbrišiUporabnika(uporabnikId);
    }



}
