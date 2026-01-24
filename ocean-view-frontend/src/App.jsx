import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard';
import Navbar from './components/Navbar/Navbar';
import { ThemeProvider } from './components/ThemeContext';
import AdminEditProfile from './components/Admin Tasks/AdminEditProfile';
import './App.css';
import Register from './components/Admin Tasks/LoginCredentials/Register';
import ManageUsers from './components/Admin Tasks/ManageUsers';
import Reservations from './components/Admin Tasks/Reservations';
import Fine from './components/Admin Tasks/Fine';
import Login from './components/Admin Tasks/LoginCredentials/Login';
import RecoverPassword from './components/Admin Tasks/LoginCredentials/RecoverPassword';
import ResetPassword from './components/Admin Tasks/LoginCredentials/ResetPassword';
import OtpVerification from './components/Admin Tasks/LoginCredentials/OtpVerification';
import CLogin from './components/Customer Tasks/LoginCredentials/CLogin';
import Customer_Dashboard from './components/Customer Tasks/Customer_Dashboard';
import CustomerNavbar from './components/Navbar/CustomerNavbar';
import CEditProfile from './components/Customer Tasks/CustomerEditProfile';
import CustomerHistory from './components/Customer Tasks/CustomerHistory';
import CustomerReservations from './components/Customer Tasks/CustomerReservations';
import HelpSection from './components/Customer Tasks/helpSection';
import Set from './components/Admin Tasks/Settings';

function App() {
  return (
    <ThemeProvider>
      <BrowserRouter>
      <Routes>
        <Route path='/' element={<Dashboard />}></Route>
        <Route path='/manage' element={<ManageUsers/>}></Route>

        <Route path="/*" element={<DashboardLayout />}></Route>
        <Route path="/navbar" element={<Navbar />} />
        <Route path='/Admin_Register' element={<Register/>}></Route>
        <Route path='/Admin_Login' element={<Login/>}></Route>
        <Route path='/Admin_ResetPassword' element={<ResetPassword/>}></Route>
        <Route path='/Admin_RecoverPassword' element={<RecoverPassword/>}></Route>
        <Route path='/OtpVerification' element={<OtpVerification/>}></Route>
        <Route path='/Customer_Login' element={<CLogin/>}></Route>
        <Route path='/Customer/*' element={<CustomerDashboardLayout/>}></Route>
      </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}


  function DashboardLayout() {
    return (
      <div className="dashboard-layout">
        <Navbar></Navbar>
        <main className="main-context">
          <Routes>
            <Route path="/dashboard" element= {<Dashboard/>}></Route>
            <Route path='/users' element= {<ManageUsers/>}></Route>
            <Route path='/EditProfile' element= {<AdminEditProfile/>}></Route>
            <Route path='/reservations' element= {<Reservations/>}></Route>
            <Route path='/fine' element={<Fine/>}></Route>
            <Route path='/settings' element={<Set/>}></Route>
          </Routes>
        </main>
      </div>
    );
  }

  function CustomerDashboardLayout() {
    return (
      <div className="dashboard-layout">
        <CustomerNavbar></CustomerNavbar>

        <main className="main-context">
          <Routes>
            <Route path='/Dashboard' element={<Customer_Dashboard/>}></Route>
            <Route path='/editProfile' element={<CEditProfile/>}></Route>
            <Route path='/CustomerHistory' element={<CustomerHistory/>}></Route>
            <Route path='/reservations' element={<CustomerReservations/>}></Route>
            <Route path='/helpSection' element={<HelpSection/>}></Route>
            <Route path='/settings' element={<Set/>}></Route>
          </Routes>
        </main>
      </div>
    );
  }


export default App;