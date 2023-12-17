<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ProductsSites.FoodProduct" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <style>
        body {
            display: flex;
            flex-direction:column;
            justify-content: flex-start;
            align-items: center;
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
            display: flex;
            flex-direction: column;
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
        .logout-container {
            position:relative;
            top:2rem;
            display: block;
            justify-content: flex-end;

        }
        .logout-form{
        border:none;
        }

    </style>
</head>
<body>
    <form method="post" action="deleteproductServlet">
        <h1>Delete Product</h1>
        <c:choose>
            <c:when test="${not empty prod}">
                <!-- If product is not empty -->
                <input type="hidden" name="productId" value="${prod.id}">
                <div>
                    <label for="productSKU">SKU:</label>
                    <input type="text" id="productSKU" name="productSKU" value="${prod.SKU}">
                </div>
                <div>
                    <label for="category">Category:</label>
                    <input type="text" id="category" name="category" value="${prod.category}">
                </div>
                <div>
                    <label for="productDescription">Product Description:</label>
                    <textarea id="productDescription" name="productDescription">${prod.description}</textarea>
                </div>
                <div>
                    <label for="price">Price:</label>
                    <input type="text" id="price" name="price" value="${prod.price}">
                </div>
                <!-- Other input fields for SKU, category, price, etc. -->

                <!-- Logout form inside the main form -->

                <!-- Submit button to update product -->
                <p>Are you sure you want to delete this product</p>
                   <td>
                             <a href="updateprod" class="back">Back</a>
                          </td>
                <input type="submit" value="Delete">
            </c:when>
        </c:choose>
    </form>

       <div class="logout-container">
                        <form method="post" action="logoutServlet" class="logout-form">
                            <button type="submit">Logout</button>
                        </form>
                    </div>
</body>
</html>