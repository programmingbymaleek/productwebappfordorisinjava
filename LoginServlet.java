package ProductsSites;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException { response.sendRedirect("adminlogin.jsp");}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");

            String usern = request.getParameter("username");
            String passwd = request.getParameter("password");

            String sqlQuery = "SELECT username from users where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, usern);
            preparedStatement.setString(2, passwd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // User is authenticated, set session attribute
                HttpSession session = request.getSession();
                session.setAttribute("authenticatedUser", usern);
                // Redirect to the updateprod URL
                response.sendRedirect("updateprod");
            } else {
                // User authentication failed, redirect back to login page
                response.sendRedirect("adminlogin.jsp");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}



