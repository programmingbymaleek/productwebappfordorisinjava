<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ProductsSites.FoodProduct" %>
<%@ page isELIgnored="false" %>

<head>
    <title>Add new Customer</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        form {
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
            background-color: #f9f9f9;
            width: 400px;
            margin-top: 20px; /* Added margin top */
        }
        div {
            margin-bottom: 10px;
        }
        label {
            display: inline-block;
            width: 100px;
        }
        input[type="text"] {
            width: 180px;
            padding: 5px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            padding: 8px 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        textarea {
            width: 180px;
            height: 80px;
            padding: 5px;
            border-radius: 3px;
            border: 1px solid #ccc;
            resize: vertical;
        }
    </style>
</head>
<body>
  <input type="hidden" name="productId" value="${prod.id}">

    <form method="post" action="addNewProductServlet">
        <h1>Add New Product</h1>
          <div>
                           <label for="productSKU">SKU:</label>
                           <input type="text" id="productSKU" name="productSKU">
                       </div>
                       <div>
                           <label for="category">Category:</label>
                           <input type="text" id="category" name="category">
                       </div>
                       <div>
                           <label for="productDescription">Product Description:</label>
                           <textarea id="productDescription" name="productDescription"></textarea>
                       </div>
                       <div>
                           <label for="price">Price:</label>
                           <input type="text" id="price" name="price">
                       </div>
        <!-- Other input fields for adding a new customer -->

        <!-- Submit button to add the new customer -->
        <td>
             <a href="updateprod" class="back">Back</a>
          </td>
        <input type="submit" value="Add Product">
    </form>
</body>
</html>
