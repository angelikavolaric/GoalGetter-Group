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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("SpellCheckingInspection")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/seznamiUr")
public class BelezenjeSeznamViri {

    private final UpravljanjeSeznamovUr upravljanjeSeznamovUrZrno;
    private static final Logger log = Logger.getLogger(BelezenjeSeznamViri.class.getName());

    @Context
    protected UriInfo uriInfo;

    @Inject
    public BelezenjeSeznamViri(UpravljanjeSeznamovUr upravljanjeSeznamovUr) {
        this.upravljanjeSeznamovUrZrno = upravljanjeSeznamovUr;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUreSeznam() {
        try {
            UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
            List<UreSeznam> seznamUr = ureSeznamZrna.pridobiVseUreSeznamu();
            if (seznamUr.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No time lists found.")
                        .build();
            }
            return Response.ok(seznamUr).build();
        } catch (Exception e) {
            log.severe("Error retrieving time lists: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving time lists.")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUreSeznam(UreSeznam novSeznamUr) {
        try {
            if (novSeznamUr == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid input data.")
                        .build();
            }
            UreSeznam createdSeznam = upravljanjeSeznamovUrZrno.ustvariSeznamUr(novSeznamUr);
            return Response.status(Response.Status.CREATED).entity(createdSeznam).build();
        } catch (Exception e) {
            log.severe("Error creating time list: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while creating the time list.")
                    .build();
        }
    }

    @GET
    @Path("/{ureSeznamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUreSeznam(@PathParam("ureSeznamId") int ureSeznamId) {
        try {
            UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
            UreSeznam seznamUr = ureSeznamZrna.pridobiUreSeznam(ureSeznamId);
            if (seznamUr == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Time list with ID " + ureSeznamId + " not found.")
                        .build();
            }
            return Response.ok(seznamUr).build();
        } catch (Exception e) {
            log.severe("Error retrieving time list with ID " + ureSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the time list.")
                    .build();
        }
    }

    @PUT
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putUreSeznam(@PathParam("ureSeznamId") int ureSeznamId, UreSeznam novSeznamUr) {
        try {
            UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
            UreSeznam updatedSeznam = ureSeznamZrna.posodobiSeznamUr(ureSeznamId, novSeznamUr);
            if (updatedSeznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Time list with ID " + ureSeznamId + " not found.")
                        .build();
            }
            return Response.ok(updatedSeznam).build();
        } catch (Exception e) {
            log.severe("Error updating time list with ID " + ureSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while updating the time list.")
                    .build();
        }
    }

    @DELETE
    @Path("/{ureSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUreSeznam(@PathParam("ureSeznamId") int ureSeznamId) {
        try {
            UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
            boolean result = ureSeznamZrna.odstraniSeznamUr(ureSeznamId);
            if (result) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Time list with ID " + ureSeznamId + " not found.")
                        .build();
            }
        } catch (Exception e) {
            log.severe("Error deleting time list with ID " + ureSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while deleting the time list.")
                    .build();
        }
    }

    @POST
    @Path("/{ureSeznamId}/ure")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUra(@PathParam("ureSeznamId") Integer ureSeznamId, Ure novaUra) {
        try {
            UreSeznamZrna ureSeznamZrna = new UreSeznamZrna();
            UreSeznam seznam = ureSeznamZrna.pridobiUreSeznam(ureSeznamId);
            if (seznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Time list with ID " + ureSeznamId + " not found.")
                        .build();
            }
            UreZrno ureZrno = new UreZrno();
            Ure createdUra = ureZrno.dodajUro(novaUra, seznam);
            return Response.status(Response.Status.CREATED).entity(createdUra).build();
        } catch (Exception e) {
            log.severe("Error adding time to list with ID " + ureSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while adding time to the list.")
                    .build();
        }
    }

    @GET
    @Path("/{ureSeznamId}/ure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUraSeznamUr(@PathParam("ureSeznamId") int ureSeznamId) {
        try {
            UpravljanjeSeznamovUr upravljanjeSeznamovUr = new UpravljanjeSeznamovUr();
            List<Ure> ure = upravljanjeSeznamovUr.pridobiUreVSeznamu(ureSeznamId);
            if (ure.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No times found in the time list with ID " + ureSeznamId)
                        .build();
            }
            return Response.ok(ure).build();
        } catch (Exception e) {
            log.severe("Error retrieving times for list with ID " + ureSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the times.")
                    .build();
        }
    }
}
