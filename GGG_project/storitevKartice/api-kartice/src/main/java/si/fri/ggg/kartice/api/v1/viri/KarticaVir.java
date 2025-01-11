package si.fri.ggg.kartice.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/kartice")
public class KarticaVir {

    @Context
    protected UriInfo uriInfo;

    private static final Logger log = Logger.getLogger(KarticaVir.class.getName());

    @Operation(description = "Vrne seznam kartic.", summary = "Seznam kartic")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam kartic",
                    content = @Content(schema = @Schema(implementation = Kartica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih kartic")}
            ),
            @APIResponse(responseCode = "400",
                    description = "Napaka v vhodnih podatkih",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @APIResponse(responseCode = "500",
                    description = "Napaka strežnika",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKartice() {
        try {
            KarticaZrno karticaZrno = new KarticaZrno();
            List<Kartica> kartice = karticaZrno.pridobiVseKartice();
            if (kartice.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No cards found.")
                        .build();
            }
            return Response.ok(kartice).build();
        } catch (Exception e) {
            log.severe("Error retrieving cards: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKartica(Kartica novaKartica) {
        try {
            KarticaZrno karticaZrno = new KarticaZrno();
            KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
            KarticeSeznam seznam = karticeSeznamZrno.pridobiKarticeSeznam(1);
            if (seznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Kartice seznam not found.")
                        .build();
            }
            Kartica addedKartica = karticaZrno.dodajKartico(novaKartica, seznam);
            return Response.status(Response.Status.CREATED).entity(addedKartica).build();
        } catch (Exception e) {
            log.severe("Error adding new card: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{karticaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKartica(@PathParam("karticaId") int karticaId) {
        try {
            KarticaZrno karticaZrno = new KarticaZrno();
            Kartica kartica = karticaZrno.pridobiKartico(karticaId);
            if (kartica == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card with ID " + karticaId + " not found.")
                        .build();
            }
            return Response.ok(kartica).build();
        } catch (Exception e) {
            log.severe("Error retrieving card with ID " + karticaId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{karticaId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putKartica(@PathParam("karticaId") int karticaId, Kartica novaKartica) {
        try {
            KarticaZrno karticaZrno = new KarticaZrno();
            Kartica updatedKartica = karticaZrno.urediKartico(karticaId, novaKartica);
            if (updatedKartica == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card with ID " + karticaId + " not found.")
                        .build();
            }
            return Response.ok(updatedKartica).build();
        } catch (Exception e) {
            log.severe("Error updating card with ID " + karticaId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{karticaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKartica(@PathParam("karticaId") int karticaId) {
        try {
            KarticaZrno karticaZrno = new KarticaZrno();
            boolean result = karticaZrno.izbrišiKartico(karticaId);
            if (result) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card with ID " + karticaId + " not found.")
                        .build();
            }
        } catch (Exception e) {
            log.severe("Error deleting card with ID " + karticaId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }
}
