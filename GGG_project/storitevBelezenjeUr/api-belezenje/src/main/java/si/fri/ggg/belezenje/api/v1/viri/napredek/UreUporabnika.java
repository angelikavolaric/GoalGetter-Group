package si.fri.ggg.belezenje.api.v1.viri.napredek;

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
import si.fri.ggg.belezenje.zrna.UreZrno;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("SpellCheckingInspection")
@Path("/ure/uporabnik")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class UreUporabnika {
    private static final Logger log = Logger.getLogger(UreUporabnika.class.getName());

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam vnesenih ur učenja.", summary = "Seznam ur")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ur",
                    content = @Content(schema = @Schema(implementation = Ure.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih vnosov ur")}),
            @APIResponse(responseCode = "400",
                    description = "Napaka v vhodnih podatkih",
                    content = @Content(schema = @Schema(implementation = Errors.ErrorMessage.class))),
            @APIResponse(responseCode = "500",
                    description = "Napaka strežnika",
                    content = @Content(schema = @Schema(implementation = Errors.ErrorMessage.class)))})

    @GET
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVseUreUporabnika(@PathParam("uporabnikId") int uporabnikId) {
        try {
            UreZrno ureZrno = new UreZrno();
            List<Object[]> vseUre = ureZrno.pridobiUreUp(uporabnikId);

            if (vseUre.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No study hours found.")
                        .build();
            }
            return Response.ok(vseUre)
                    .header("X-Total-Count", vseUre.size())
                    .build();
        } catch (Exception e) {
            log.severe("Error retrieving study hours: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving study hours.")
                    .build();
        }
    }
}
