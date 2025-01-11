package si.fri.ggg.uporabniki.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.ggg.uporabniki.entitete.Uporabnik;
import si.fri.ggg.uporabniki.zrna.UporabnikZrno;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/uporabniki")
public class UporabnikVir {

    @Context
    protected UriInfo uriInfo;

    private static final Logger log = Logger.getLogger(UporabnikVir.class.getName());

    @Operation(description = "Vrne seznam uporabnikov.", summary = "Seznam uporabnikov")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Seznam uporabnikov",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    ),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")}
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Uporabniki niso bili najdeni."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Notranja napaka strežnika."
            )
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUporabniki() {
        try {
            UporabnikZrno uporabnikZrno = new UporabnikZrno();
            List<Uporabnik> uporabniki = uporabnikZrno.pridobiVseUporabnike();
            if (uporabniki.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No users found.")
                        .build();
            }
            return Response.ok(uporabniki).build();
        } catch (Exception e) {
            log.severe("Error retrieving users: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUporabnik(Uporabnik novUporabnik) {
        try {
            UporabnikZrno uporabnikZrno = new UporabnikZrno();
            Uporabnik createdUser = uporabnikZrno.dodajUporabnika(novUporabnik);
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } catch (Exception e) {
            log.severe("Error adding new user: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUporabnik(@PathParam("uporabnikId") int uporabnikId) {
        try {
            UporabnikZrno uporabnikZrno = new UporabnikZrno();
            Uporabnik uporabnik = uporabnikZrno.pridobiUporabnika(uporabnikId);
            if (uporabnik == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User with ID " + uporabnikId + " not found")
                        .build();
            }
            return Response.ok(uporabnik).build();
        } catch (Exception e) {
            log.severe("Error retrieving user with ID " + uporabnikId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{uporabnikId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUporabnik(@PathParam("uporabnikId") int uporabnikId, Uporabnik novaUporabnik) {
        try {
            UporabnikZrno uporabnikZrno = new UporabnikZrno();
            Uporabnik updatedUser = uporabnikZrno.urediUporabnika(uporabnikId, novaUporabnik);
            if (updatedUser == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User with ID " + uporabnikId + " not found")
                        .build();
            }
            return Response.ok(updatedUser).build();
        } catch (Exception e) {
            log.severe("Error updating user with ID " + uporabnikId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{uporabnikId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUporabnik(@PathParam("uporabnikId") int uporabnikId) {
        try {
            UporabnikZrno uporabnikZrno = new UporabnikZrno();
            boolean result = uporabnikZrno.izbrišiUporabnika(uporabnikId);
            if (result) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User with ID " + uporabnikId + " not found")
                        .build();
            }
        } catch (Exception e) {
            log.severe("Error deleting user with ID " + uporabnikId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }
}
