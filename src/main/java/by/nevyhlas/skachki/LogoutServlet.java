package by.nevyhlas.skachki;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");



        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println("<div style=\"position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);\">");
        out.println("<h1>Logout</h1>");
        out.println("<form action=\"logout\" method=\"post\">");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</div>");

        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        //delete cookie named "username"
        session.removeAttribute("username");

        session.invalidate();
        response.sendRedirect("/");
    }
}
