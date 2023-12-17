package productSiteTerminal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

//I will be using records to check if there is a recrod in the db.
record DataExists(String SKU, String description, String category, int price) {

}

public class DataBaseConnections {
    private String url;
    private String username;
    private String password;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        // Database connection parameters
        DataBaseConnections dataBaseConnections = new DataBaseConnections();
        dataBaseConnections.databaseConnection();


        boolean flag = true;
        while (flag) {
            printActionOptions();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> dataBaseConnections.listAllProducts();
                    case 2 -> {
                        System.out.println("Enter an id for which you want to get the item for:");
                        int id = Integer.parseInt(scanner.nextLine());
                        dataBaseConnections.findAllProduct(id);
                    }
                    case 3 -> {
                        System.out.println("Enter SKU Value:");
                        String SKU = scanner.nextLine();
                        System.out.println("Enter description:");
                        String description = scanner.nextLine();
                        System.out.println("Enter category:");
                        String category = scanner.nextLine();
                        System.out.println("Enter price:");
                        int price = Integer.parseInt(scanner.nextLine());

                        FoodProduct foodproduct = new FoodProduct(SKU, description, category, price);
                        dataBaseConnections.addNewProduct(foodproduct);
                    }
                    case 4 -> {
                        System.out.println("Eneter the record id.. ");
                        int recordToFind = Integer.parseInt(scanner.nextLine());
                        dataBaseConnections.updateAProduct(recordToFind);
                    }

                    case 5->{
                        System.out.println("Enter the rocord you what deleted");
                        int recordId = Integer.parseInt(scanner.nextLine());
                        dataBaseConnections.deleteRecord(recordId);
                    }

                    default -> flag = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void databaseConnection() {
        this.password = "user";
        this.url = "jdbc:mysql://localhost:3306/foodstore";
        this.username = "user";
    }

    private void listAllProducts() throws SQLException {
        ArrayList<FoodProduct> foodProductList = new ArrayList<>();
        // Establishing a connection to the database
        Connection connection = DriverManager.getConnection(url, username, password);
        // Creating a SQL statement (example of a SELECT query)
        String sqlQuery = "SELECT * FROM foodproduct";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        // Executing the query
        ResultSet resultSet = preparedStatement.executeQuery();
        // Processing the results
        while (resultSet.next()) {
            // Example: Retrieving values from the result set
            int id = resultSet.getInt("id");
            String SKU = resultSet.getString("SKU");
            String description = resultSet.getString("description");
            String category = resultSet.getString("category");
            int price = resultSet.getInt("price");
            FoodProduct foodproduct = new FoodProduct(id, SKU, description, category, price);
            foodProductList.add(foodproduct);
            //print out all food in the database in an arraylist
        }
        ArrayList<String> formattedProducts = new ArrayList<>();
        for (FoodProduct food : foodProductList) {
            //    Do something with the retrieved data
            String formattedProduct = "Product={id=\"" + food.getId() + "\",SKU=\"" + food.getSKU() + "\",description=\"" +
                    "" + food.getDescription() + "\",category=\"" + food.getCategory() + "\",Price=\"" + food.getPrice() +
                    "\"}";
            formattedProducts.add(formattedProduct);
        }
        // Print the formatted data
        for (String formattedProduct : formattedProducts) {
            System.out.println(formattedProduct);
        }
    }

    private static void printActionOptions() {
        System.out.println();
        String actions = """
                [1]  List  all products
                [2] Search for product by ID
                [3] Add a new product
                [4] Update a product by ID
                [5] Delete a product by ID
                [6] Exit
                Enter a number for which action you what to perform""";
        System.out.println(actions + " ");
    }

    private void findAllProduct(int idd) throws SQLException {
        // Establishing a connection to the database
        Connection connection = DriverManager.getConnection(url, username, password);
        // Creating a SQL statement (example of a SELECT query)
        String sqlQuery = "SELECT * FROM foodproduct WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, idd);
            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("description");
                int price = resultSet.getInt("price");
                ArrayList<String> formattedProducts = new ArrayList<>();

                String retrivedProduct = "Product={id=\"" + id + "\",SKU=\"" + SKU + "\",description=\"" +
                        "" + description + "\",category=\"" + category + "\"}";
                formattedProducts.add(retrivedProduct);
                // Print out the information of persons with the searched age
                for (String retrivedFormatedProduct : formattedProducts) {
                    System.out.println(retrivedFormatedProduct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addNewProduct(FoodProduct foodproduct) throws SQLException {
        // Establishing a connection to the database
        Connection connection = DriverManager.getConnection(url, username, password);

        String insertQuery = "INSERT INTO foodproduct (SKU,description,category,price) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, foodproduct.getSKU());
            preparedStatement.setString(2, foodproduct.getDescription());
            preparedStatement.setString(3, foodproduct.getCategory());
            preparedStatement.setInt(4, foodproduct.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add product.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


    }

    private void updateAProduct(int recordToFind) throws SQLException {

            if (!isDataPresent(recordToFind).isEmpty()) {
                // Establishing a connection to the database
                Connection connection = DriverManager.getConnection(url, username, password);
                // Creating a SQL statement (example of a SELECT query)
                System.out.println(isDataPresent(recordToFind));
                System.out.println("""
                        Are you Sure you want to update this product in the database?\n
                        type YES/Y to confirm or NO/N to cancel.""");
                String enteredValue = scanner.nextLine().trim().toLowerCase();
                System.out.println(enteredValue);
                if (!enteredValue.equals("yes") && !enteredValue.equals("y")) {
                    System.out.println("No  update was made");
                    return;
                }
                // Creating a SQL statement (example of a SELECT query)
                System.out.println("Enter SKU number");
                String SKU = scanner.nextLine();
                System.out.println("Enter Description");
                String description = scanner.nextLine();
                System.out.println("Enter Category");
                String category = scanner.nextLine();
                System.out.println("Enter Price");
                int price = Integer.parseInt(scanner.nextLine());
                String updateSqlQuery = "UPDATE foodproduct SET SKU = ?, description = ?, category = ?, price = ? " +
                        "WHERE " +
                        "id = ?";
                try (PreparedStatement upDatePreparedStatement = connection.prepareStatement(updateSqlQuery)) {
                    upDatePreparedStatement.setString(1, SKU);
                    upDatePreparedStatement.setString(2, description);
                    upDatePreparedStatement.setString(3, category);
                    upDatePreparedStatement.setInt(4, price);
                    upDatePreparedStatement.setInt(5, recordToFind);
                    int rowsAffected = upDatePreparedStatement.executeUpdate();
                    System.out.println(rowsAffected + "row(s) updated.");
                }

            } else {
                System.out.println("No record found..");
                return;
            }

    }




    private void deleteRecord(int recordToDelete) throws SQLException {
        if(isDataPresent(recordToDelete).isEmpty()){
            System.out.println("Invalid data record");
            return;
        }

        // Establishing a connection to the database
        System.out.println(isDataPresent(recordToDelete));
        System.out.println("""
                        Are you Sure you want to Delete this product from the database?\n
                        type YES/Y to confirm or NO/N to cancel.""");
        String enteredValue = scanner.nextLine().trim().toLowerCase();
        System.out.println(enteredValue);
        if (!enteredValue.equals("yes") && !enteredValue.equals("y")) {
            System.out.println("No  update was made");
            return;
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        // Creating a SQL statement (example of a SELECT query)
        String sqlQueryDelete = "DELETE FROM foodproduct WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQueryDelete)) {
            preparedStatement.setInt(1, recordToDelete);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted.");
        }

    }

    private String isDataPresent(int recordID)throws SQLException{
        String endProd ="";
        // Establishing a connection to the database
        Connection connection = DriverManager.getConnection(url, username, password);
        // Creating a SQL statement (example of a SELECT query)
        String sqlQuery = "SELECT * FROM foodproduct WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, recordID);
            // Executing the query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("description");
                int price = resultSet.getInt("price");
                ArrayList<DataExists> productToFind = new ArrayList<>();
                productToFind.add(new DataExists(SKU, description, category, price));
                endProd = productToFind.toString();
            }

        }
        return  endProd;

    }

}





