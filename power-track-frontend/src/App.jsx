import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import AddHouseForm from './pages/AddHouseForm';
import HousePage from './pages/HousePage';
import AddDeviceForm from './pages/AddDeviceForm';
import ReportDetailsPage from './pages/ReportDetailsPage';
import DeviceDetailsPage from './pages/DeviceDetailsPage';
import EditDeviceForm from './pages/EditDeviceForm';
import EditUserForm from './pages/EditUserForm';
import EditHouseForm from './pages/EditHouseForm';
import './styles/global.css';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(!!localStorage.getItem('token'));

  // Обновляем состояние при изменении localStorage
  useEffect(() => {
    const handleStorageChange = () => {
      setIsAuthenticated(!!localStorage.getItem('token'));
    };

    window.addEventListener('storage', handleStorageChange);
    return () => window.removeEventListener('storage', handleStorageChange);
  }, []);

  return (
    <Router>
      <Routes>
        {/* Маршруты для аутентификации */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Защищенные маршруты (требуют авторизации) */}
        <Route
          path="/dashboard"
          element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />}
        />
        <Route
          path="/add-house"
          element={isAuthenticated ? <AddHouseForm /> : <Navigate to="/login" />}
        />
        <Route
          path="/house/:houseId"
          element={isAuthenticated ? <HousePage /> : <Navigate to="/login" />}
        />
        <Route
          path="/add-device/:houseId"
          element={isAuthenticated ? <AddDeviceForm /> : <Navigate to="/login" />}
        />
        <Route
          path="/report/:houseId/:reportId"
          element={isAuthenticated ? <ReportDetailsPage /> : <Navigate to="/login" />}
        />
        <Route
          path="/device/:houseId/:deviceId"
          element={isAuthenticated ? <DeviceDetailsPage /> : <Navigate to="/login" />}
        />
        <Route
          path="/edit-device/:houseId/:deviceId"
          element={isAuthenticated ? <EditDeviceForm /> : <Navigate to="/login" />}
        />
        <Route
          path="/settings"
          element={isAuthenticated ? <EditUserForm /> : <Navigate to="/login" />}
        />
        <Route
          path="/edit-house/:houseId"
          element={isAuthenticated ? <EditHouseForm /> : <Navigate to="/login" />}
        />
        {/* Перенаправление на /login по умолчанию */}
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;