<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ProductsSites.FoodProduct" %>

<html>
<head>
    <title>Edit items</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
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
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
            font-weight: 600;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        a {
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
        .edit-link {
            margin-right: 10px;
        }
              .addproduct-link {
                            text-align: center;
                            margin-top: 20px;
                        }
                        .addproduct-link a {
                            text-decoration: none;
                            color: #007bff;
                            font-weight: bold;
                        }
             .logout-container{
             display:flex;
             justify-content:center;
             align-items:center;
             }
             button{width:8rem; height:3rem; font-size:1.2rem;margin-top:1rem;}

    </style>
</head>
<body>
    <h1>Edit Product</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>SKU</th>
            <th>Description</th>
            <th>Category</th>
            <th>Price</th>
            <th>Edit</th>
            <th>Delete</th>
            <!-- Add other columns as needed -->
        </tr>
        <%
            List<FoodProduct> productList = (List<FoodProduct>) request.getAttribute("productlist");
            if (productList != null) {
                for (FoodProduct product : productList) {
        %>
                    <tr>
                        <td><%= product.getId() %></td>
                        <td><%= product.getSKU() %></td>
                        <td><%= product.getDescription() %></td>
                        <td><%= product.getCategory() %></td>
                        <td><%= product.getPrice() %></td>
                        <td>
                            <a href="editProductServlet?id=<%= product.getId() %>" class="edit-link">Edit</a>
                        </td>
                        <td>
                            <a href="deleteproductServlet?id=<%= product.getId() %>">Delete</a>
                        </td>
                    </tr>
        <%
                }
            }
        %>
    </table>
      <div class="addproduct-link"><a href="addnewproduct.jsp">Add a new Product</a></div>
             <div class="logout-container">
                              <form method="post" action="logoutServlet" class="logout-form">
                                  <button type="submit">Logout</button>
                              </form>
                          </div>
</body>
</html>
