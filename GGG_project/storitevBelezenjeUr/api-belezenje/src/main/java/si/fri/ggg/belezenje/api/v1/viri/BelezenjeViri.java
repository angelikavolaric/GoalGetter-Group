package si.fri.ggg.belezenje.api.v1.viri;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.glassfish.jersey.internal.Errors;
import si.fri.ggg.belezenje.entitete.Ure;
import si.fri.ggg.belezenje.entitete.UreSeznam;
import si.fri.ggg.belezenje.zrna.UreSeznamZrna;
import si.fri.ggg.belezenje.zrna.UreZrno;

@SuppressWarnings("SpellCheckingInspection")
@Path("/ure")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")

public class BelezenjeViri {

    @Context
    protected UriInfo uriInfo;



    @Operation(description = "Vrne seznam vnesenih ur učenja.", summary = "Seznam ur")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ur",
                    content = @Content(schema = @Schema(implementation = Ure.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih vnosov ur")}
            ),
            @APIResponse(responseCode = "400",
                    description = "Napaka v vhodnih podatkih",
                    content = @Content(schema = @Schema(implementation = Errors.ErrorMessage.class))
            ),
            @APIResponse(responseCode = "500",
                    description = "Napaka strežnika",
                    content = @Content(schema = @Schema(implementation = Errors.ErrorMessage.class))
            )})



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ure> getVseUre() {
        UreZrno ureZrno = new UreZrno();
        return ureZrno.pridobiVseUre();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ure postUre(Ure novaUra) {
        UreZrno ureZrno = new UreZrno();
        UreSeznamZrna ureSeznamZrno = new UreSeznamZrna();
        UreSeznam seznam = ureSeznamZrno.pridobiUreSeznam(1);
        return ureZrno.dodajUro(novaUra, seznam);
    }

    @GET
    @Path("/{uraId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ure getUre(@PathParam("uraId") int uraId) {
        UreZrno ureZrno = new UreZrno();
        return ureZrno.pridobiUro(uraId);
    }

    @PUT
    @Path("/{uraId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Ure putUre(@PathParam("uraId") int uraId, Ure novaUra) {
        UreZrno ureZrno = new UreZrno();
        return ureZrno.urediUro(uraId, novaUra);
    }


    @DELETE
    @Path("/{uraId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUre(@PathParam("uraId") int uraId) {
        UreZrno ureZrno = new UreZrno();
        boolean result = ureZrno.odstraniUro(uraId);
    }




   /* @GET
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ure> getVseUreUporabnika(@PathParam("uporabnikId") int uporabnikId) {
        UreZrno ureZrno = new UreZrno();
        return ureZrno.pridobiVseUreUporabnika();
    }*/





}





