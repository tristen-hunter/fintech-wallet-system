import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom"
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import SignupPage from "./pages/SignupPage";
import MainLayout from "./layouts/MainLayout";
import Wallets from "./pages/Wallets";
import Transactions from "./pages/Transactions";
import ProtectedRoutes from "./utils/ProtectedRoutes";
import { AuthProvider } from "./context/AuthContext"; // Import the Provider

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
            {/* Public Routes: Accessible to everyone */}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />

            {/* Protected Area: useAuth works here because it's inside AuthProvider */}
            <Route element={<ProtectedRoutes />}>
                <Route path="/" element={<MainLayout />}>
                  <Route index element={<Navigate to="/dashboard" replace />} />
                  <Route path="dashboard" element={<Dashboard />} />
                  <Route path="wallets" element={<Wallets />} />
                  <Route path="transactions" element={<Transactions />} />
                </Route>
            </Route>
            
            {/* Optional: Catch-all redirect */}
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App