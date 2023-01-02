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

@WebServlet(name = "updateBalanceServlet", value = "/updateBalance")
public class updateBalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //make balance update form
        if (request.getSession().getAttribute("username") == null) {
            response.sendRedirect("/login");
            return;
        }

        String username = (String) request.getSession().getAttribute("username");

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
        out.println("<h2>Update balance</h2>");
        out.println("<form action=\"updateBalance\" method=\"post\">");
        //cant be null

        out.println("<p>Balance: <input type=\"number\" name=\"balance\" min=\"0\" required></p>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("<a href=\"main\">Main</a>");
        out.println("</body>");

        out.println("</html>");




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //get username
        String username = (String) request.getSession().getAttribute("username");


        //get balance
        int balance = Integer.parseInt(request.getParameter("balance"));


        //update balance
        updateBalance(username, balance);
        response.sendRedirect("/balance");
    }

    private void updateBalance(String username, int balance) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skachki", "root", "1234");
            Statement statement = connection.createStatement();

            //get current balance
            int currentBalance = 0;
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM wallet WHERE userID = (SELECT userID FROM user WHERE username = '" + username + "')");

            if (resultSet.next()) {
                currentBalance = resultSet.getInt("balance");
            }


            statement.executeUpdate("UPDATE wallet SET balance = " + (balance + currentBalance) + " WHERE userID = (SELECT userID FROM user WHERE username = '" + username + "')");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
