package by.nevyhlas.skachki;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import sql
import java.sql.*;


@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Login";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");



        out.println("<div style=\"position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);\">");

        //if error=1, show error message
        String error = request.getParameter("error");
        String message = "";
        System.out.println(error);
        if (error == null) {

        }
        else {
            switch (error) {
                case "AuthError":
                    message = "Wrong username or password";
                    break;
                case "BanError":
                    message = "You are banned";
                    break;
                default:
                    message = "";
            }

            out.println("<h3>" + message + "</h3>");
        }


        out.println("<h1>" + this.message + "</h1>");
        out.println("<form action=\"login\" method=\"post\">");
        out.println("<label for=\"username\">Username:</label>");
        out.println("<input type=\"text\" id=\"username\" name=\"username\"><br><br>");
        out.println("<label for=\"password\">Password:</label>");
        out.println("<input type=\"password\" id=\"password\" name=\"password\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        //add a link from right to register page
        out.println("<a href=\"register\" style=\"float: right;\">Register</a>");
        out.println("</form>");
        out.println("</div>");



        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        //sql connection
        try {
            //load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //create connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/skachki", "root", "1234");
            //create statement
            Statement stmt = conn.createStatement();
            //execute query
            ResultSet rs = stmt.executeQuery("select * from user where username = '" + username + "' and password = '" + password + "'");
            //process result
            if (rs.next()) {
                //if user exists
                //create session
                HttpSession session = request.getSession();
                //set session attribute
                session.setAttribute("username", username);
                //redirect to profile
                response.sendRedirect("main");
            } else {
                //if user does not exist
                //redirect to login and add error message
                response.sendRedirect("login?error=AuthError");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }



    public void destroy() {
    }
}