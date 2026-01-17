import './ManageUsers.css';

function Reservations() 
{
    return(
        <div className="table-content">
            <div className="heading">
                <span>Manage Reservations</span>
            </div>

            <div className="table-container">
                <div className="users-table">

                   
                    <thead  className="table-head">
                        <tr>
                            <th>ID</th>
                            <th>Profile</th>
                            <th>Full Name</th>
                            <th>Customer Email</th>
                            <th>Contact</th>
                            <th>Date Borrowed</th>
                            <th>Date Handovering</th>
                            <th>Fine</th>
                            <th>Total Payable</th>
                            <th>Total Paid</th>
                            <th>Payment Id</th>
                            <th>Payment Method</th>
                            <th>Status</th>
                            <th>Manage</th>
                        </tr>
                    </thead>

                    <tbody  className="table-body"> 
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>John doe</td>
                            <td>Email</td>
                            <td>Contact</td>
                            <td>Date borrowed</td>
                            <td>Date handovering</td>
                            <td>Fine</td>
                            <td>Total Payable</td>
                            <th>Rs. Paid</th>
                            <th>Payment ID</th>
                            <th>payment method</th>
                            <td>Status</td>
                            
                            <td>
                                <button className="delete-btn">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </tbody>
                    
                </div>
            </div>
        </div>
    )

}

export default Reservations;