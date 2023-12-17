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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/updateprod")
public class viewproductUserServerlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        HttpSession session = request.getSession(true);
        if (session != null && session.getAttribute("authenticatedUser") != null) {
            List<FoodProduct> productList = new ArrayList<>();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodstore", "user", "user");
                Statement statement = connection.createStatement();
                String sqlQuery = "SELECT * FROM foodproduct";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    System.out.println("======================");
                    int id = resultSet.getInt("id");
                    String SKU = resultSet.getString("SKU");
                    String description = resultSet.getString("description");
                    String category = resultSet.getString("category");
                    int price = resultSet.getInt("price");
                    FoodProduct product = new FoodProduct(id, SKU, description, category, price);
                    productList.add(product);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println(productList);
            request.setAttribute("productlist", productList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("editproduct.jsp");
            dispatcher.forward(request, response);

        }

    }
}
