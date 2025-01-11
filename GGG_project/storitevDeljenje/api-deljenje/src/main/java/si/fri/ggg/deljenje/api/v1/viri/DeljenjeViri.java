package si.fri.ggg.deljenje.api.v1.viri;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import si.fri.ggg.deljenje.entitete.Cilj;
import si.fri.ggg.deljenje.zrna.CiljZrno;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/deljenje")
public class DeljenjeViri {

    private final CiljZrno ciljZrno;
    //private final UpravljanjeSeznamovUr upravljanjeSeznamovUrZrno;

    @Context
    protected UriInfo uriInfo;

    @javax.inject.Inject
    public DeljenjeViri(CiljZrno ciljZrno) {
        this.ciljZrno = ciljZrno;
    }

    private static final Logger log = Logger.getLogger(DeljenjeViri.class.getName());




    @Operation(description = "Vrne seznam vnesenih ciljev učenja.", summary = "Seznam ciljev")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ciljev",
                    content = @Content(schema = @Schema(implementation = Cilj.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih ciljev")}
            )})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVseCilje() {
        try {
            CiljZrno ciljZrno = new CiljZrno();
            List<Cilj> vsi = ciljZrno.pridobiVseCilje();
            if (vsi.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No goals found.")
                        .build();
            }
            return Response.ok(vsi)
                    .header("X-Total-Count", vsi.size())
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
    public Response postCilj(Cilj novCilj) {
        try{
            Cilj ustvarjenCilj = ciljZrno.ustvariCilj(novCilj);
            return Response.status(Response.Status.CREATED).entity(ustvarjenCilj).build();
        } catch (Exception e) {
            log.severe("Error adding new study goal: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while adding the study goal.")
                    .build();
        }
    }

    @GET
    @Path("/{ciljId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUre(@PathParam("ciljId") int ciljId) {
        try {
            CiljZrno clijZrno = new CiljZrno();
            Cilj cilj = clijZrno.pridobiCilj(ciljId);
            if (cilj == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study goal with ID " + ciljId + " not found.")
                        .build();
            }
            return Response.ok(cilj).build();
        } catch (Exception e) {
            log.severe("Error retrieving study goal with ID " + ciljId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the study goal.")
                    .build();
        }
    }

    @PUT
    @Path("/{ciljId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUre(@PathParam("ciljId") int ciljId, Cilj novCilj) {
        try {
            CiljZrno ciljZrno = new CiljZrno();
            Cilj posodobljenCilj = ciljZrno.urediCilj(ciljId, novCilj);
            if (posodobljenCilj == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study goal with ID " + ciljId + " not found.")
                        .build();
            }
            return Response.ok(posodobljenCilj).build();
        } catch (Exception e) {
            log.severe("Error updating study goal with ID " + ciljId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while updating the study goal.")
                    .build();
        }
    }

    @DELETE
    @Path("/{ciljId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUre(@PathParam("ciljId") int ciljId) {


        try {
            CiljZrno ciljZrno = new CiljZrno();
            boolean result = ciljZrno.odstraniCilj(ciljId);
            if (!result) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Study goal with ID " + ciljId + " not found.")
                        .build();
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.severe("Error deleting study goal with ID " + ciljId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while deleting the study goal.")
                    .build();
        }
    }



}
