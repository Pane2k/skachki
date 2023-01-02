package by.nevyhlas.skachki;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "MainServlet", value = "/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if cookie JSESSIONID is founded auto login user

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skachki", "root", "1234");
            Statement statement = connection.createStatement();

            //if username and JSESSIONID is founded in database auto login user
            if (request.getSession().getAttribute("username") == null ) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("SESSIONID")) {
                        String JSESSIONID = cookie.getValue();
                        String sql = "SELECT username FROM user WHERE JSESSIONID = '" + JSESSIONID + "'";
                        ResultSet resultSet = statement.executeQuery(sql);
                        if (resultSet.next()) {
                            HttpSession session = request.getSession();
                            session.setAttribute("username", sql);
                            Cookie ck = new Cookie("username", sql);
                            ck.setMaxAge(60 * 60 * 24 * 30);
                            response.addCookie(ck);
                            response.sendRedirect("/main");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




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
            out.println("<a href=\"balance\">Balance</a>");
        }
        out.println("</body>");
        out.println("</html>");





    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void userCookieLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get cookie named "username"
        Cookie[] cookies = request.getCookies();
        String username = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                username = cookie.getValue();
            }
        }
        //if cookie is not null, set session attribute "username" to cookie value
        if (username != null) {
            request.getSession().setAttribute("username", username);
        }
    }
}
