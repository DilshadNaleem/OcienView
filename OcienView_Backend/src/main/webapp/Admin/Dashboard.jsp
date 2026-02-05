<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Panel</title>

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

  <style>

    body {
      font-family: Arial, sans-serif;
      background-color: #f7f7f7;
    }

    .header-content {
      background-color: #f5f5f5;
      padding: 10px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .logo {
      background-color: #D63A36;
      color: white;
      padding: 10px;
      font-size: 18px;
      font-weight: bold;
    }

    .header-date {
      font-size: 14px;
      color: #333;
    }

    .info-menu .profile img {
      width: 40px;
      border-radius: 50%;
    }

    /* Sidebar Styles */
    .sidebar {
      background-color: #2d3436;
      color: white;
      width: 230px;
      position: fixed;
      height: 100%;
      padding-top: 20px;
    }

    .sidebar ul {
      list-style-type: none;
      padding: 0;
    }

    .sidebar ul li {
      padding: 15px 20px;
    }

    .sidebar ul li a {
      color: white;
      display: flex;
      align-items: center;
      text-decoration: none;
      font-size: 16px;
    }

    .sidebar ul li a:hover {
      background-color: #333;
      border-left: 4px solid #D63A36;
      color: white;
    }

    .sidebar ul .submenu {
      display: none;
    }

    .sidebar ul li:hover > .submenu {
      display: block;
    }

    /* Dashboard Cards */
    .page {
      margin-left: 250px;
      padding: 20px;
    }

    .container-fluid {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 20px;
    }

    .card {
      background-color: white;
      padding: 30px;
      border-radius: 8px;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
      text-align: center;
      font-size: 20px;
      font-weight: bold;
    }

    .card i {
      font-size: 40px;
      margin-bottom: 10px;
    }

    .card:nth-child(1) {
      background-color: #bdc3c7;
    }

    .card:nth-child(2) {
      background-color: #e74c3c;
      color: white;
    }

    .card:nth-child(3) {
      background-color: #3498db;
      color: white;
    }

    .card:nth-child(4) {
      background-color: #f39c12;
      color: white;
    }

    .card:nth-child(5) {
      background-color: #2ecc71;
      color: white;
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
      .container-fluid {
        grid-template-columns: 1fr;
      }

      .sidebar {
        width: 100%;
        height: auto;
        position: relative;
      }

      .page {
        margin-left: 0;
      }
    }

  </style>

</head>
<body>
  <header id="header">
    <div class="logo pull-left"> Admin Portal</div>
    <div class="header-content">
      <div class="header-date pull-left">
        <strong></strong>
      </div>
      <div class="pull-right clearfix">
        <ul class="info-menu list-inline list-unstyled">
          <li class="profile">
            <a href="#" data-toggle="dropdown" class="toggle" aria-expanded="false">
              <span> <i class="caret"></i></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="profile.php"><i class="glyphicon glyphicon-user"></i> Profile</a></li>
              <li><a href="edit_account.php"><i class="glyphicon glyphicon-cog"></i> Settings</a></li>
              <li class="last"><a href="logout.php"><i class="glyphicon glyphicon-off"></i> Logout</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </header>

  <div class="sidebar">
    <ul>
      <li><a href="/Admin/Dashboard.jsp"><i class="glyphicon glyphicon-home"></i> Dashboard</a></li>
      <li><a href="#" class="submenu-toggle"><i class="glyphicon glyphicon-user"></i> User Management</a>
        <ul class="nav submenu">

          <li><a href="/Admin/ManageCustomers">Manage Customer</a></li>

        </ul>
      </li>
      <li><a href="/Mega_City/ManageVehicleTypesServlet"><i class="glyphicon glyphicon-indent-left"></i> Manage Categories</a>
      <ul class="nav submenu">
        <li><a href="/Admin/AddNewRoomTypes.html">Add Categories</a></li>
      </ul>
      </li>
      <li><a href="#" class="submenu-toggle"><i class="glyphicon glyphicon-th-large"></i> Products</a>
        <ul class="nav submenu">
          <li><a href="/Admin/AddNewRoomTypes.html">Manage Room Types</a></li>

        </ul>

      <li>
    <a href="/Admin/ViewAllBookings" class="submenu-toggle">
        <i class="glyphicon glyphicon-list-alt"></i> View All Bookings
    </a>




      <li><a href="/Admin/Signing.jsp" class="submenu-toggle"><i class="fas fa-sign-out-alt"></i> Logout</a></li>


    </ul>
  </div>

  <div class="page">
  <div class="container-fluid">

    <a href="/Admin/EditProfile" class="card">
      <i class="glyphicon glyphicon-user"></i>  Edit Profile
    </a>

    <a href="/Admin/AddNewRoomTypes.html" class="card">
      <i class="glyphicon glyphicon-shopping-cart"></i>  Manage Room Types
    </a>
    <a href="/Admin/AddRoom.jsp" class="card">
      <i class="glyphicon glyphicon-th"></i>  Manage Rooms
    </a>

  </div>
</div>

</body>
</html>