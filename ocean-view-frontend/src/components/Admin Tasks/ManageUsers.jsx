import './ManageUsers.css';

function ManageUsers() 
{
    return(
        <div className="table-content">
            <div className="heading">
                <span>Manage Customers</span>
            </div>

            <div className="table-container">
                <div className="users-table">

                   
                    <thead  className="table-head">
                        <tr>
                            <th>ID</th>
                            <th>Profile</th>
                            <th>Full Name</th>
                            <th>NIC</th>
                            <th>Email</th>
                            <th>Contact Number</th>
                            <th>Status</th>
                            <th>Total Calculations</th>
                            <th>Manage</th>
                        </tr>
                    </thead>

                    <tbody  className="table-body"> 
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>John doe</td>
                            <td>NIC</td>
                            <td>Email</td>
                            <td>Contact</td>
                            <td>Status</td>
                            <td>Rs.Total Calc</td>
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

export default ManageUsers;