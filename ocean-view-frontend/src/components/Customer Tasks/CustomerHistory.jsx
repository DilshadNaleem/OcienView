import { FaSearch } from 'react-icons/fa';

function CustomerHistory() 
{
    return(
        <div className="table-content">
            <div className="heading">
                <span>Customer History</span>
            </div>

            <div className="table-container">
                <div className="users-table">

                       <div className="search-container">
                    <div className="search-bar">
                        <input type="text" name="search" id="search"
                        placeholder='Search' className='search'/>
                         <FaSearch className='search-icon'/>
                    </div>
                   </div>
                   
                    <thead  className="table-head">
                        <tr>
                            <th>Reservation ID</th>
                            <th>Full Name</th>
                            <th>Price</th>
                            <th>Fine</th>
                            <th>Borrowed Date</th>
                            <th>Hand Overed Date</th>
                            <th>Extra Days</th>
                            <th>Total Calculations</th>
                            <th>Reservation Status</th>
                            <th>Payment ID</th>
                            <th>Payment Method</th>
                            <th>Manage</th>
                        </tr>
                    </thead>

                    <tbody  className="table-body"> 
                        <tr>
                            <td>1</td>
                            <td>John doe</td>
                            <td>Price</td>
                            <td>Rs.Fine</td>
                            <td>Borrowed Dae</td>
                            <td>Handovered Date</td>
                            <td>Extra Date</td>
                            <td>Rs.Total Calc</td>
                            <td>Reservation Status</td>
                            <td>Payment ID</td>
                            <td>Payment Method</td>
                            <td>
                                <div className="button-history">
                                <button className="delete-btn">
                                    Cancel
                                </button>
                                    
                                <button className="edit-btn">
                                    Edit
                                </button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                    
                </div>
            </div>
        </div>
    )

}

export default CustomerHistory;