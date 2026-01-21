import { FaSearch } from 'react-icons/fa';


function Reservations() 
{
    return(
        <div className="table-content">
            <div className="heading">
                <span>Manage Reservations</span>
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
                            <th>ID</th>
                            <th>Profile</th>
                            <th>Full Name</th>
                            <th>Customer Email</th>
                            
                            <th>Date Borrowed</th>
                            <th>Date Handovering</th>
                            <th>Fine</th>
                            <th>Total Payable</th>
                            
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
                            <td>Date borrowed</td>
                            <td>Date handovering</td>
                            <td>Fine</td>
                            <td>Total Payable</td>
                           
                            <td>Payment ID</td>
                            <td>payment method</td>
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