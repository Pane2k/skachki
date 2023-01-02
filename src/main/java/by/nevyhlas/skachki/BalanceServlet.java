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

@WebServlet(name = "BalanceServlet", value = "/balance")
public class BalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //if user is not logged in
        if (request.getSession().getAttribute("username") == null) {
            response.sendRedirect("/");
            return;
        }

        //get username
        String username = (String) request.getSession().getAttribute("username");



        int balance = getBalance(username);
        
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
        out.println("<h2>Balance</h2>");
        out.println("<a href=\"update_balance\">Update balance</a>");
        out.println("<p>Balance: " + balance + "</p>");
        out.println("<a href=\"main\">Main</a>");
        out.println("</body>");
        out.println("</html>");



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected int getBalance(String username) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skachki", "root", "1234");;
            Statement statement = connection.createStatement();

            //get balance from database
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM wallet WHERE userID = (SELECT userID FROM user WHERE username = '" + username + "')");
            if(resultSet.next()) {
                return resultSet.getInt("balance");
            }
            else {
                //make new wallet with 0 balance and copy userID from user table
                statement.executeUpdate("INSERT INTO wallet (balance, userID) VALUES (0, (SELECT userID FROM user WHERE username = '" + username + "'))");
                return 0;

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
