import {BrowserRouter, Routes, Route} from 'react-router-dom';
import Dashboard from './components/Dashboard/Dashboard';
import Navbar from './components/Navbar/Navbar';
import { ThemeProvider } from './components/ThemeContext';
import './App.css';
import { BsExposure } from 'react-icons/bs';

function App() {
  return (
    <ThemeProvider>
      <BrowserRouter>
      <Routes>
        <Route path='/' element={Dashboard}></Route>


        <Route path="/*" element={<DashboardLayout />}></Route>
        <Route path="/navbar" element={<Navbar />} />
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
            <Route path="/dashboard" element= {Dashboard}></Route>
          </Routes>
        </main>
      </div>
    );
  }


export default App;