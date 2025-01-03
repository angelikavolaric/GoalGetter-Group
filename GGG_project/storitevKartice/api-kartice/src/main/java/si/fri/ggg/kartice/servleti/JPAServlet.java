package si.fri.ggg.kartice.servleti;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;
import si.fri.ggg.kartice.zrna.UpravljanjeKarticSeznamZrno;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Inject
    private KarticaZrno karticaZrno; // EJB for Kartica-related logic

    @Inject
    private UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno; // EJB for KarticeSeznam-related logic

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("<br/><br/>Storitev kartice:<br/>");
        writer.append("/v1/kartice <br> /v1/kartice/{karticaId} <br>");
        writer.append("/v1/seznamiKartic <br> /v1/seznamiKartic/{seznamKarticId} <br>/v1/seznamiKartic/{seznamKarticId}/kartice <br>");

        // Get the KarticeSeznam ID from the request
        /*String seznamIdStr = req.getParameter("seznamId");
        Integer seznamId = null;

        if (seznamIdStr != null) {
            try {
                seznamId = Integer.parseInt(seznamIdStr);
            } catch (NumberFormatException e) {
                log.warning("Invalid KarticeSeznam ID format: " + seznamIdStr);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        // Fetch Kartica entries for the specific KarticeSeznam if ID is provided
        List<Kartica> kartice = null;
        if (seznamId != null) {
            KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
            kartice = karticeSeznamZrno.pridobiKarticeSeznam(seznamId);
            if (kartice == null || kartice.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().append("{\"message\": \"No kartice found for the given seznamId.\"}");
                return;
            }
        } else {
            // If no seznamId, return all Kartica entities
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().append("{\"message\": \"No seznamID found.\"}");
            //kartice = karticaZrno.pridobiKartice();
        }

        // Set the response content type to JSON
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Write the Kartica list as a JSON response
        try (PrintWriter writer = resp.getWriter()) {
            writer.append("{ \"kartice\": [");

            for (int i = 0; i < kartice.size(); i++) {
                Kartica kartica = kartice.get(i);
                writer.append("{");
                writer.append("\"id\": ").append(String.valueOf(kartica.getId())).append(",");
                writer.append("\"vprasanje\": \"").append(kartica.getVprasanje()).append("\",");
                writer.append("\"odgovor\": \"").append(kartica.getOdgovor()).append("\"");
                writer.append("}");

                if (i < kartice.size() - 1) {
                    writer.append(", ");
                }
            }

            writer.append("]}");
        }*/
    }
}

/*@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Inject
    private KarticaZrno karticaZrno; // EJB for Kartica-related logic

    @Inject
    private UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno; // EJB for KarticeSeznam-related logic

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the KarticeSeznam ID from the request
        /*resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        // izpis uporabnikov
        writer.append("<br/><br/>Uporabniki:<br/>");
        writer.append("Bo treba pa še kaj narediti preden se kaj izpiše tu :))" + "<br/><br/>");
*

        String seznamIdStr = req.getParameter("seznamId");
        Integer seznamId = null;
        if (seznamIdStr != null) {
            try {
                seznamId = Integer.parseInt(seznamIdStr);
            } catch (NumberFormatException e) {
                log.warning("Invalid KarticeSeznam ID format: " + seznamIdStr);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        // If no ID is provided, return all Kartica entities
        List<Kartica> kartice = null;

        if (seznamId != null) {
            // Fetch Kartica entries for a specific KarticeSeznam
            KarticeSeznamZrno karticeSeznamZrno = new KarticeSeznamZrno();
            kartice = karticeSeznamZrno.pridobiKarticeSeznam(seznamId);
        } else {
            // If no seznamId, just get all Kartica entities
            PrintWriter writer = resp.getWriter();
            writer.append("Error pridobivanje kartic seznam");
        }

        // Set the response content type
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Generate the response (for simplicity, let's just print Kartica details in JSON format)
        PrintWriter writer = resp.getWriter();
        writer.append("{ \"kartice\": [");

        for (int i = 0; i < kartica.size(); i++) {
            Kartica kartica = kartica.get(i);
            writer.append("{");
            writer.append("\"id\": ").append(String.valueOf(kartica.getId())).append(",");
            writer.append("\"vprasanje\": \"").append(kartica.getVprasanje()).append("\",");
            writer.append("\"odgovor\": \"").append(kartica.getOdgovor()).append("\"");
            writer.append("}");

            if (i < kartice.size() - 1) {
                writer.append(", ");
            }
        }

        writer.append("]}");
    }
}*/
//////////////////////////////////////////////////////////////////////////////


/*package si.fri.ggg.kartice.servleti;

/*import si.fri.ggg.kartice.dtos.KarticaDto;
import si.fri.ggg.kartice.dtos.KarticeSeznamDto;
import si.fri.ggg.kartice.zrna.KarticaZrno;
import si.fri.ggg.kartice.zrna.KarticeSeznamZrno;
import si.fri.ggg.kartice.entitete.KarticeSeznam;*

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import si.fri.ggg.kartice.zrna.KarticaZrno;
import  si.fri.ggg.kartice.entitete.Kartica;
import si.fri.ggg.kartice.zrna.UpravljanjeKarticSeznamZrno;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    /*@Inject
    private KarticaZrno karticaZrno;

    @Inject
    private UpravljanjeKarticSeznamZrno upravljanjeKarticSeznamZrno;*

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        // izpis uporabnikov
        writer.append("<h1>StoritevKartice - servlet<h1/>");
    }
}*/