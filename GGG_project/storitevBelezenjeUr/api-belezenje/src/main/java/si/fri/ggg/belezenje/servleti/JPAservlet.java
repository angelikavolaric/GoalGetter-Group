package si.fri.ggg.belezenje.servleti;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebServlet("/servlet")
public class JPAservlet extends HttpServlet{

    private static final Logger log = Logger.getLogger(JPAservlet.class.getName());



        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            resp.setContentType("text/html; charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter writer = resp.getWriter();

            writer.append("<br/><br/>Uporabniki:<br/>");
            writer.append("Bo treba pa še kaj narediti preden se kaj izpiše tu :)" + "<br/><br/>");

        }

}
