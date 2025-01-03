package si.fri.ggg.uporabniki.servleti;

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

import si.fri.ggg.uporabniki.entitete.Uporabnik;
import si.fri.ggg.uporabniki.zrna.UporabnikZrno;
import si.fri.ggg.uporabniki.zrna.UpravljanjeUporabnikovZrno;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(JPAServlet.class.getName());

    @Inject
    private UporabnikZrno uporabnikZrno; // EJB for Uporabnik-related logic

    @Inject
    private UpravljanjeUporabnikovZrno upravljanjeUporabnikovZrno; // EJB for UporabnikiSeznam-related logic

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.append("<br/><br/>Storitev uporabniki:<br/>");
        writer.append("/v1/uporabniki <br> /v1/uporabniki/{uporabnikId} <br>");


    }
}