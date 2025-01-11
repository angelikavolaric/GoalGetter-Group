package si.fri.ggg.belezenje.api.v1.viri;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("SpellCheckingInspection")
@Path("/ure")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class BelezenjeViri {

    private static final Logger log = Logger.getLogger(BelezenjeViri.class.getName());

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVseUre() {
        try {
            UreZrno ureZrno = new UreZrno();
            List<Ure> vseUre = ureZrno.pridobiVseUre();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUre(Ure novaUra) {
        try {
            if (novaUra == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid input data.")
                        .build();
            }
            UreZrno ureZrno = new UreZrno();
            UreSeznamZrna ureSeznamZrno = new UreSeznamZrna();
            UreSeznam seznam = ureSeznamZrno.pridobiUreSeznam(1);
            if (seznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study list not found.")
                        .build();
            }
            Ure createdUra = ureZrno.dodajUro(novaUra, seznam);
            return Response.status(Response.Status.CREATED).entity(createdUra).build();
        } catch (Exception e) {
            log.severe("Error adding new study hour: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while adding the study hour.")
                    .build();
        }
    }

    @GET
    @Path("/{uraId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUre(@PathParam("uraId") int uraId) {
        try {
            UreZrno ureZrno = new UreZrno();
            Ure ura = ureZrno.pridobiUro(uraId);
            if (ura == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study hour with ID " + uraId + " not found.")
                        .build();
            }
            return Response.ok(ura).build();
        } catch (Exception e) {
            log.severe("Error retrieving study hour with ID " + uraId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the study hour.")
                    .build();
        }
    }

    @PUT
    @Path("/{uraId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUre(@PathParam("uraId") int uraId, Ure novaUra) {
        try {
            UreZrno ureZrno = new UreZrno();
            Ure updatedUra = ureZrno.urediUro(uraId, novaUra);
            if (updatedUra == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study hour with ID " + uraId + " not found.")
                        .build();
            }
            return Response.ok(updatedUra).build();
        } catch (Exception e) {
            log.severe("Error updating study hour with ID " + uraId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while updating the study hour.")
                    .build();
        }
    }

    @DELETE
    @Path("/{uraId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUre(@PathParam("uraId") int uraId) {
        try {
            UreZrno ureZrno = new UreZrno();
            boolean result = ureZrno.odstraniUro(uraId);
            if (!result) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study hour with ID " + uraId + " not found.")
                        .build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.severe("Error deleting study hour with ID " + uraId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while deleting the study hour.")
                    .build();
        }
    }
}
