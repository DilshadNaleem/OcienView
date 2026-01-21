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
        <Route path='Admin_Login' element={<Login/>}></Route>
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
          </Routes>
        </main>
      </div>
    );
  }


export default App;