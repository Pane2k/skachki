package by.nevyhlas.skachki;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Registration";
    }




    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");


        out.println("<div style=\"position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);\">");
        out.println("<h1>" + message + "</h1>");
        out.println("<form action=\"register\" method=\"post\">");
        out.println("<label for=\"username\">Username:</label>");
        out.println("<input type=\"text\" id=\"username\" name=\"username\"><br><br>");
        out.println("<label for=\"password\">Password:</label>");
        out.println("<input type=\"password\" id=\"password\" name=\"password\"><br><br>");
        out.println("<label for=\"email\">Email:</label>");
        out.println("<input type=\"email\" id=\"email\" name=\"email\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        //add a link from right to register page
        out.println("<a href=\"login\" style=\"float: right;\">login</a>");
        out.println("</form>");
        out.println("</div>");


        out.println("</body></html>");
    }

    //get strings from form
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //if username or password or email is empty, redirect to register page with error=NotFullFilledInfo

        if(username == "" || password == "" || email == ""){
            response.sendRedirect("register?error=NotFullFilledInfo");
        }

        //check if username and email is already taken in database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connect to database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skachki", "root", "1234");;
            Statement statement = connection.createStatement();

            //check if username or email is already taken
            System.out.println("trying to find user");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE username = '" + username + "' OR email = '" + email + "'");
            if (resultSet.next()) {
                response.sendRedirect("register?error=UsernameOrEmailTaken");
            }
            else {
                //add user to database
                System.out.println("trying to add user");

                statement.executeUpdate("INSERT INTO user (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "')");
                response.sendRedirect("login");
            }


        }
        catch (Exception throwable) {
            throwable.printStackTrace();
        }


    }

    public void destroy() {
    }
}
