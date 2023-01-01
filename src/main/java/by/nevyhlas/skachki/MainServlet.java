package by.nevyhlas.skachki;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MainServlet", value = "/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Skachki</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Skachki</h1>");

        //if user is not logged in
        if (request.getSession().getAttribute("username") == null) {
            out.println("<a href=\"login\">Login</a>");
            out.println("<a href=\"register\">Register</a>");
        }
        else {
            out.println("<a href=\"logout\">Logout</a>");
        }
        out.println("</body>");
        out.println("</html>");



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
