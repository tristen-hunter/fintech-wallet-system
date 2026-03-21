import { BrowserRouter, Routes, Route } from "react-router-dom"

import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import SignupPage from "./pages/SignupPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/signup" element={<SignupPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
