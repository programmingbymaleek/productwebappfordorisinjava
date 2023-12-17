<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ProductsSites.FoodProduct" %>

<html>
<head>
    <title>Product List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: #fff;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-products {
            text-align: center;
            margin-top: 20px;
            color: #666;
        }
        .login-link {
            text-align: center;
            margin-top: 20px;
        }
        .login-link a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>Product List</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>SKU</th>
            <th>Description</th>
            <th>Category</th>
            <th>Price</th>
            <!-- Add other columns as needed -->
        </tr>
        <% List<FoodProduct> productList = (List<FoodProduct>) request.getAttribute("product");
        if (productList != null && !productList.isEmpty()) {
            for (FoodProduct product : productList) { %>
                <tr>
                    <td><%= product.getId() %></td>
                    <td><%= product.getSKU() %></td>
                    <td><%= product.getDescription() %></td>
                    <td><%= product.getCategory() %></td>
                    <td><%= product.getPrice() %></td>
                    <!-- Access other product properties accordingly -->
                </tr>
        <% } } else { %>
                <tr>
                    <td colspan="5" class="no-products">No products available</td>
                </tr>
        <% } %>
    </table>
    <div class="login-link"><a href="adminlogin.jsp">Admin Login</a></div>
</body>
</html>