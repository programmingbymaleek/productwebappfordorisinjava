<!DOCTYPE html>
<html>
<head>
    <title>Confirmation</title>
    <script type="text/javascript">
        function confirmUpdate() {
            var confirmResult = confirm("Are you sure you want to update?");
            if (confirmResult) {
                document.getElementById("confirmationForm").submit(); // Submit the form
            } else {
                window.history.back(); // Go back if not confirmed
            }
        }
    </script>
</head>
<body onload="confirmUpdate()">
    <form id="confirmationForm" method="post" action="editProductServlet">
        <input type="hidden" name="confirmation" value="true">
        <!-- Add other hidden inputs with updated data here -->
    </form>
</body>
</html>