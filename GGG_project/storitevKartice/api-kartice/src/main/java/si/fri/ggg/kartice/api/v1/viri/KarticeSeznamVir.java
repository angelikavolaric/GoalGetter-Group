package si.fri.ggg.kartice.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.entitete.KarticeSeznam;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;
import si.fri.ggg.kartice.zrna.UpravljanjeKarticSeznamZrno;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("SpellCheckingInspection")
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/seznamiKartic")
public class KarticeSeznamVir {

    private final KarticeSeznamZrno karticeSeznamZrno;
    private final UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno;
    @Context
    protected UriInfo uriInfo;

    private static final Logger log = Logger.getLogger(KarticeSeznamVir.class.getName());

    @javax.inject.Inject
    public KarticeSeznamVir(KarticeSeznamZrno karticeSeznamZrno, UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno) {
        this.karticeSeznamZrno = karticeSeznamZrno;
        this.upravljanjeKarticSeznamZrno = upravljanjeKarticSeznamZrno;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKarticeSeznam() {
        try {
            List<KarticeSeznam> seznamKartic = karticeSeznamZrno.pridobiVseKarticeSeznamu();
            if (seznamKartic.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No card lists found.")
                        .build();
            }
            return Response.ok(seznamKartic).build();
        } catch (Exception e) {
            log.severe("Error retrieving card lists: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKarticeSeznam(KarticeSeznam novKarticeSeznam) {
        try {
            if (novKarticeSeznam == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid input data.")
                        .build();
            }
            KarticeSeznam createdSeznam = upravljanjeKarticSeznamZrno.ustvariKarticeSeznam(novKarticeSeznam);
            return Response.status(Response.Status.CREATED).entity(createdSeznam).build();
        } catch (Exception e) {
            log.severe("Error creating card list: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{karticaSeznamId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKarticaSeznam(@PathParam("karticaSeznamId") int karticaSeznamId) {
        try {
            KarticeSeznam seznamKartic = karticeSeznamZrno.pridobiKarticeSeznam(karticaSeznamId);
            if (seznamKartic == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card list with ID " + karticaSeznamId + " not found.")
                        .build();
            }
            return Response.ok(seznamKartic).build();
        } catch (Exception e) {
            log.severe("Error retrieving card list with ID " + karticaSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{karticaSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putKarticaSeznam(@PathParam("karticaSeznamId") int karticaSeznamId, KarticeSeznam novKarticeSeznam) {
        try {
            KarticeSeznam updatedSeznam = karticeSeznamZrno.posodobiKarticeSeznam(karticaSeznamId, novKarticeSeznam);
            if (updatedSeznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card list with ID " + karticaSeznamId + " not found.")
                        .build();
            }
            return Response.ok(updatedSeznam).build();
        } catch (Exception e) {
            log.severe("Error updating card list with ID " + karticaSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{karticaSeznamId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteKarticeSeznam(@PathParam("karticaSeznamId") int karticaSeznamId) {
        try {
            boolean result = karticeSeznamZrno.odstraniKarticeSeznam(karticaSeznamId);
            if (result) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card list with ID " + karticaSeznamId + " not found.")
                        .build();
            }
        } catch (Exception e) {
            log.severe("Error deleting card list with ID " + karticaSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/{karticaSeznamId}/kartice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKartica(@PathParam("karticaSeznamId") Integer karticaSeznamId, Kartica novaKartica) {
        try {
            KarticeSeznam seznam = karticeSeznamZrno.pridobiKarticeSeznam(karticaSeznamId);
            if (seznam == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Card list with ID " + karticaSeznamId + " not found.")
                        .build();
            }
            KarticaZrno karticaZrno = new KarticaZrno();
            Kartica createdKartica = karticaZrno.dodajKartico(novaKartica, seznam);
            //createdKartica.setKarticeSeznam(seznam);
            return Response.status(Response.Status.CREATED).entity(createdKartica).build();
        } catch (Exception e) {
            log.severe("Error adding card to list with ID " + karticaSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{karticaSeznamId}/kartice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKarticaSeznamKartice(@PathParam("karticaSeznamId") int karticaSeznamId) {
        try {
            List<Kartica> kartice = upravljanjeKarticSeznamZrno.pridobiKarticeVSeznamu(karticaSeznamId);
            if (kartice.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No cards found in the card list with ID " + karticaSeznamId)
                        .build();
            }
            return Response.ok(kartice).build();
        } catch (Exception e) {
            log.severe("Error retrieving cards for list with ID " + karticaSeznamId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Internal server error: " + e.getMessage())
                    .build();
        }
    }
}
