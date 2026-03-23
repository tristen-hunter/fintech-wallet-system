import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom"

import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import SignupPage from "./pages/SignupPage";
import MainLayout from "./layouts/MainLayout";
import Wallets from "./pages/Wallets";
import Transactions from "./pages/Transactions";
import Reports from "./pages/Reports";
import ProtectedRoutes from "./utils/ProtectedRoutes";

function App() {
  return (
    <BrowserRouter>
      <Routes>
            {/*No Sidebar or Topbar*/}
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />

            <Route element={<ProtectedRoutes />}>
                {/*private route that is wrapped in the Mainlayout structure*/}
                <Route path="/" element={<MainLayout />}>
                  <Route index element={<Navigate to="/dashboard" replace />} />
                  <Route path="dashboard" element={<Dashboard />} />

                  <Route path="wallets" element={<Wallets />} />
                  <Route path="transactions" element={<Transactions />} />
                  <Route path="reports" element={<Reports />} />
                </Route>
            </Route>
            
      </Routes>
    </BrowserRouter>
  )
}

export default App