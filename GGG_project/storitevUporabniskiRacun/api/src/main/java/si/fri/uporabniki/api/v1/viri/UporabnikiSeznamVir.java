package si.fri.uporabniki.api.v1.viri;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@SuppressWarnings("SpellCheckingInspection")
@ApplicationScoped
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
@Path("/seznamUporabnikov")
public class UporabnikiSeznamVir {


    @Context
    protected UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getKartice(){
        return "{\"sporocilo\" : \"Pridobi uporabniki\"}";
    }


    // TODO: missing implementation

}