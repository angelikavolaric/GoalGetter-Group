package si.fri.ggg.timer.api.v1.viri;


import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.ggg.timer.api.servlet.TimerAPI;
import si.fri.ggg.timer.entitete.Timer;
import si.fri.ggg.timer.entitete.TimerEnt;
import si.fri.ggg.timer.zrna.TimerZrna;

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

@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")

@Path("/timers")
public class TimerViri {


    private final TimerAPI timerAPI = new TimerAPI();
    private final TimerZrna timerZrna = new TimerZrna();

    /*@Inject
    public TimerViri (TimerAPI timerAPI, TimerZrna timerZrna){
        this.timerZrna = timerZrna;
        this.timerAPI = timerAPI;
    }*/
    private static final Logger log = Logger.getLogger(TimerViri.class.getName());

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Vrne seznam vnesenih časovnikov.", summary = "Seznam časovnikov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam časovnikov",
                    content = @Content(schema = @Schema(implementation = TimerEnt.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih ciljev")}
            )})

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTimers() {
        try {
            TimerZrna timerZrna = new TimerZrna();
            List<TimerEnt> vsi = timerZrna.pridobiVseTimere();

            if (vsi.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No timers found.")
                        .build();
            }

            return Response.ok(vsi)
                    .header("X-Total-Count", vsi.size())
                    .build();
        } catch (Exception e) {
            //log.severe("Error retrieving timers: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving timers.")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postTimer(TimerEnt novTimer) {
        try{
            String ID = timerAPI.setTimer(novTimer.getOpis(), novTimer.getZacetek(), novTimer.getKonec());
            novTimer.setOuterAPIid(ID);
            TimerEnt ustvariTimer = timerZrna.ustvariTimer(novTimer);




            return Response.status(Response.Status.CREATED).entity(ustvariTimer).build();
        } catch (Exception e) {
            log.severe("Error adding new timer: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while adding the timer.")
                    .build();
        }
    }

    @GET
    @Path("/{timerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTimer(@PathParam("timerId") int timerId) {
        try {
            TimerEnt timerEnt = timerZrna.pridobiTimer(timerId);
            //Cilj cilj = clijZrno.pridobiCilj(ciljId);
            if (timerEnt == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timer with ID " + timerId + " not found.")
                        .build();
            }
            return Response.ok(timerEnt).build();
        } catch (Exception e) {
            log.severe("Error retrieving timer with ID " + timerId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while retrieving the timer.")
                    .build();
        }
    }

    @DELETE
    @Path("/{timerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTimer(@PathParam("timerId") int timerId) {


        try {
            TimerEnt timerEnt = timerZrna.pridobiTimer(timerId);
            boolean res = timerZrna.odstraniTimer(timerId);

            if (!res) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Timer with ID " + timerId + " not found.")
                        .build();
            }
            String Id = timerEnt.getOuterAPIid();
            timerAPI.deleteTimeEntry(Id);

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            log.severe("Error deleting timer with ID " + timerId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while deleting the timer.")
                    .build();
        }
    }

}
