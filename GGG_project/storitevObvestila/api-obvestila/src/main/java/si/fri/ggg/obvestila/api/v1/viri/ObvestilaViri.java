package si.fri.ggg.obvestila.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.ggg.obvestila.api.servlet.ObvestilaAPI;
import si.fri.ggg.obvestila.entitete.ObvestilaEnt;
import si.fri.ggg.obvestila.zrna.ObvestilaZrna;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS", allowOrigin = "http://localhost:4200")
@Path("/obvestila")
public class ObvestilaViri {

    private final ObvestilaAPI obvestilaAPI = new ObvestilaAPI();
    private final ObvestilaZrna obvestilaZrna = new ObvestilaZrna();

    private static final Logger log = Logger.getLogger(ObvestilaViri.class.getName());

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam vnesenih obvestil.", summary = "Seznam obvestil")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam obvestil",
                    content = @Content(schema = @Schema(implementation = ObvestilaEnt.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih obvestil")})
    })

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllObvestila() {
        try {
            List<ObvestilaEnt> vsi = obvestilaZrna.pridobiVseObvestila();

            if (vsi.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No obvestila found.")
                        .build();
            }

            return Response.ok(vsi)
                    .header("X-Total-Count", vsi.size())
                    .build();
        } catch (Exception e) {
            log.severe("Error retrieving obvestila: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving obvestila.")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postObvestilo(ObvestilaEnt novoObvestilo) {
        try {
            String apiId = obvestilaAPI.sendMessage(novoObvestilo.getZadeva(), novoObvestilo.getSporocilo());
            novoObvestilo.setOuterAPIid(apiId);

            ObvestilaEnt ustvariObvestilo = obvestilaZrna.ustvariObvestilo(novoObvestilo);

            return Response.status(Response.Status.CREATED).entity(ustvariObvestilo).build();
        } catch (Exception e) {
            log.severe("Error adding new obvestilo: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while adding the obvestilo.")
                    .build();
        }
    }

    @GET
    @Path("/{obvestiloId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObvestilo(@PathParam("obvestiloId") int obvestiloId) {
        try {
            ObvestilaEnt obvestiloEnt = obvestilaZrna.pridobiObvestilo(obvestiloId);

            if (obvestiloEnt == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Obvestilo with ID " + obvestiloId + " not found.")
                        .build();
            }
            return Response.ok(obvestiloEnt).build();
        } catch (Exception e) {
            log.severe("Error retrieving obvestilo with ID " + obvestiloId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the obvestilo.")
                    .build();
        }
    }

    @DELETE
    @Path("/{obvestiloId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteObvestilo(@PathParam("obvestiloId") int obvestiloId) {
        try {
            ObvestilaEnt obvestiloEnt = obvestilaZrna.pridobiObvestilo(obvestiloId);
            boolean res = obvestilaZrna.odstraniObvestilo(obvestiloId);

            if (!res) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Obvestilo with ID " + obvestiloId + " not found.")
                        .build();
            }


            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.severe("Error deleting obvestilo with ID " + obvestiloId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while deleting the obvestilo.")
                    .build();
        }
    }
}
