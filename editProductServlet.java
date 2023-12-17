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

@WebServlet("/editProductServlet")
public class editProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Retrieve existing session if exists
        // Check if the user is not logged in
        if (session == null || session.getAttribute("authenticatedUser") == null) {
            response.sendRedirect("adminlogin.jsp"); // Redirect to login page if not logged in
            return; // Stop further execution
        }

        int productId = Integer.parseInt(request.getParameter("id"));
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
                out.println(product.getId()+" "+product.getPrice()+" "+product.getDescription());
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (product != null) {
                out.println("not null");
                request.setAttribute("prod", product);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editprod.jsp");
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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");

            String productSKU = request.getParameter("productSKU");
            String category = request.getParameter("category");
            String productDescription = request.getParameter("productDescription");
            String price = request.getParameter("price");
            String productId = request.getParameter("productId");
            String sqlQuery = "UPDATE foodproduct SET SKU=?, description=?, category=?, price=? WHERE id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, productSKU);
            preparedStatement.setString(2, productDescription);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, price);
            preparedStatement.setString(5, productId);

            int rowsUpdated = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (rowsUpdated > 0) {
                response.sendRedirect("updateprod"); // Redirect after successful update
            } else {
                response.sendRedirect("adminlogin.jsp"); // Redirect if no update occurred
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }

    }
}



