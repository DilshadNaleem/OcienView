
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>

<html>
    <head>
        <title>Payment History</title>
        <!-- Link to Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            /* Custom CSS for color scheme */
            .pay-button {
                background-color: #0073bb;
                color: white;
                border: none;
                padding: 5px 10px;
                cursor: pointer;
                border-radius: 4px;
            }
            .pay-button:hover {
                background-color: #5196c1;
            }

            /* Modal Styles */
            .modal-content {
                background-color: white;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 30%;
                text-align: center;
                border-radius: 8px;
            }
            .modal button {
                margin: 5px;
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                border-radius: 4px;
            }
            .credit-btn { background-color: #007bff; color: white; }
            .debit-btn { background-color: #17a2b8; color: white; }
            .cash-btn { background-color: #28a745; color: white; }
            .close-btn { background-color: red; color: white; }
            .submit-btn {
                background-color: #28a745;
                color: white;
                padding: 10px 15px;
                cursor: pointer;
                border-radius: 4px;
            }
            .submit-btn:hover {
                background-color: #218838;
            }

            /* Back button */
            .back-btn {
                background-color: #0073bb;
                color: white;
                padding: 8px 15px;
                border: none;
                cursor: pointer;
                border-radius: 4px;
                margin-bottom: 20px;
            }
            .back-btn:hover {
                background-color: #5196c1;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Payment History</h2>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Vehicle Name</th>
                        <th>Booked Date</th>
                        <th>Customer Name</th>
                        <th>Fine</th> <!-- New Fine column -->
                        <th>Fare</th> <!-- Fare column -->
                        <th>Total Fare with Fine</th> <!-- Total Fare with Fine column -->
                        <th>Action</th>
                        <th>Status</th> <!-- New Status column -->
                    </tr>
                </thead>
                <tbody>
                    <%
                    ArrayList<PaymentClass> historyList = (ArrayList<PaymentClass>) request.getAttribute("historyList");

                    if (historyList != null && !historyList.isEmpty()) {
                        // DecimalFormat to format fine and fare to 2 decimal places
                        DecimalFormat df = new DecimalFormat("0.00");

                        for (PaymentClass history : historyList) {
                            String status = history.getStatus(); // Assuming there's a getStatus method
                            double fine = history.getFine(); // Get fine value
                            double fare = history.getTotal(); // Get fare value
                            double totalFine = fine + fare; // Fine + Total Fare

                            // Format the values
                            String fineFormatted = df.format(fine);
                            String fareFormatted = df.format(fare);
                            String totalFineFormatted = df.format(totalFine);
                    %>
                    <tr>
                        <td><%= history.getOrderId() %></td>
                        <td><%= history.getVehicleName() %></td>
                        <td><%= history.getCreatedat() %></td> <!-- Assuming createdat is the booking date -->
                        <td><%= history.getCustomeremail() %></td>
                        <td>Rs. <%= fineFormatted %></td> <!-- Fine -->
                        <td>Rs. <%= fareFormatted %></td> <!-- Total Fare -->
                        <td>Rs. <%= totalFineFormatted %></td> <!-- Total Fare with Fine -->

                        <td>
                            <% if (!"Completed".equals(status)) { %>
                                <button class="pay-button" id="payButton_<%= history.getOrderId() %>" onclick="openPaymentModal('<%= history.getOrderId() %>', '<%= history.getTotal() %>', this)">Pay</button>
                            <% } else { %>
                                <!-- Display "Paid" label if already completed -->
                                <label id="paidLabel_<%= history.getOrderId() %>" style="color: green; font-weight: bold; display: inline;">Paid</label>
                            <% } %>
                        </td>
                        <td>
                            <%= "Completed".equals(status) ? "Paid" : "Unpaid" %> <!-- Display the status in the new column -->
                        </td>
                    </tr>
                    <%
                        } // End of loop
                    } else {
                    %>
                    <tr>
                        <td colspan="9" style="text-align: center;">No payment history found.</td>
                    </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
            <button class="back-btn" onclick="window.history.back();">Back</button>

            <!-- Payment Method Modal -->
            <div id="paymentModal" class="modal">
                <div class="modal-content">
                    <h3>Select Payment Method</h3>
                    <form id="paymentForm" action="ProcessPaymentServlet" method="post">
                        <input type="hidden" id="orderIdInput" name="orderId" value="">
                        <input type="hidden" id="paymentMethodInput" name="paymentMethod">
                        <input type="hidden" id="totalAmountInput" name="total_amount">

                        <div id="creditFields" style="display:none;">
                            <label>Card Number:</label>
                            <input type="text" name="cardNumber" required><br><br>

                            <label>Expiration Date:</label>
                            <input type="month" name="expiryDate" required><br><br>

                            <label>CVV:</label>
                            <input type="password" name="cvv" required><br><br>
                        </div>

                        <button type="button" class="credit-btn" onclick="submitPayment('Credit')">Credit</button>
                        <button type="button" class="debit-btn" onclick="submitPayment('Debit')">Debit</button>
                        <button type="button" class="cash-btn" onclick="submitPayment('Cash')">Cash</button>
                        <button type="button" class="close-btn" onclick="closeModal()">Cancel</button>

                        <!-- Submit button for Credit and Debit -->
                        <button type="submit" id="submitPaymentBtn" class="submit-btn" style="display:none;">Submit Payment</button>
                    </form>
                </div>
            </div>

            <script>
                function openPaymentModal(orderId, totalAmount, button) {
                    // Disable the button to prevent multiple clicks
                    button.disabled = true;
                    button.innerHTML = "Processing..."; // Change button text to indicate processing
                    document.getElementById('orderIdInput').value = orderId;
                    document.getElementById('totalAmountInput').value = totalAmount;
                    document.getElementById('paymentModal').style.display = 'block';

                    // Store button ID for later re-enabling
                    document.getElementById('paymentForm').setAttribute('data-button-id', button.id);
                }

                function closeModal() {
                    document.getElementById('paymentModal').style.display = 'none';

                    // Re-enable the button when the modal is closed
                    var buttonId = document.getElementById('paymentForm').getAttribute('data-button-id');
                    if (buttonId) {
                        var button = document.getElementById(buttonId);
                        button.disabled = false;  // Re-enable the button
                        button.innerHTML = "Pay"; // Reset the button text to "Pay"
                    }
                }

                function submitPayment(method) {
                    var orderId = document.getElementById('orderIdInput').value;
                    var totalAmount = document.getElementById('totalAmountInput').value;

                    document.getElementById('paymentMethodInput').value = method;

                    // Show card fields for Credit/Debit
                    if (method === 'Credit' || method === 'Debit') {
                        document.getElementById('creditFields').style.display = 'block';
                        document.getElementById('submitPaymentBtn').style.display = 'inline';  // Show Submit button
                    } else {
                        document.getElementById('creditFields').style.display = 'none';
                        document.getElementById('submitPaymentBtn').style.display = 'none';  // Hide Submit button for Cash
                        // Submit form for Cash payment directly
                        document.getElementById('paymentForm').submit();
                    }

                    // Hide the "Pay" button and show "Paid" label after payment submission
                    document.getElementById('payButton_' + orderId).style.display = 'none';
                    document.getElementById('paidLabel_' + orderId).style.display = 'inline';  // Show "Paid" label
                }
            </script>
        </div>
    </body>
</html>
