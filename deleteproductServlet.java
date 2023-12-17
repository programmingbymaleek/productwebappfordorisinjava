package ProductsSites;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import static java.lang.System.out;

@WebServlet("/deleteproductServlet")
public class deleteproductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Retrieve existing session if exists
        // Check if the user is not logged in
        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect("adminlogin.jsp"); // Redirect to login page if not logged in
            return; // Stop further execution
        }

        int productId = Integer.parseInt(request.getParameter("id"));
        out.println(productId);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");
            String sqlQuery = "SELECT * FROM foodproduct WHERE id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            FoodProduct product = null;
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                product = new FoodProduct(id, SKU, description, category, price);
                out.println(product.getId() + " " + product.getPrice() + " " + product.getDescription());
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (product != null) {
                out.println("not null");
                request.setAttribute("prod", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteproduct.jsp");
                dispatcher.forward(request, response);
            } else {
                // Handle when no product is found with the given ID
                response.sendRedirect("noProductFound.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out.println("est here");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");

            String productID = request.getParameter("productId");

            out.println(productID);

            String sqlQuery = "DELETE FROM foodproduct WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, productID);

            int rowsDeleted = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsDeleted > 0) {
                // Redirect after successful deletion
                response.sendRedirect("updateprod");
            } else {
                // Redirect if no deletion occurred
                response.sendRedirect("adminlogin.jsp");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}



