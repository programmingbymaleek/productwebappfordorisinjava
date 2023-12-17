package ProductsSites;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/addNewProductServlet")
public class addNewProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Retrieve existing session if exists
        // Check if the user is not logged in
        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect("adminlogin.jsp"); // Redirect to login page if not logged in
            return; // Stop further execution
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");

            String SKU = request.getParameter("productSKU");
            String category = request.getParameter("category");
            String description = request.getParameter("productDescription");
            String price = request.getParameter("price");

            String sqlQuery = "INSERT INTO foodproduct (SKU, category, description, price) VALUES ( ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, SKU);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, price);


            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsInserted > 0) {
                response.sendRedirect("updateprod"); // Redirect after successful addition
            } else {
                response.sendRedirect("adminlogin.jsp"); // Redirect if no addition occurred
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
