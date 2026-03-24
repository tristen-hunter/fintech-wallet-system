import { useState } from 'react'
import { Mail, Lock, LogIn } from "lucide-react";
import { login } from "../api/authApi.ts";
import { useAuth } from '../context/AuthContext.tsx';
import { useNavigate } from 'react-router-dom';


const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const { loginUser } = useAuth();
    const navigate = useNavigate();


    // Send Data to the backend
    const handleLogin = async () => {
        try {
            // 1. Request to Spring Boot
            const response = await login({ email, password });

            // 2. The backend returned your UserfetchDTO
            const userData = response.data;

            // 3. Update the Global State (This sets 'user' in AuthContext)
            loginUser(userData);

            // 4. Send the user to the dashboard
            navigate("/dashboard");
            
            console.log("Login successful for:", userData.userName)
        } catch (error) {
            console.error("Login failed:", error);
            alert("Invalid credentials. Please try again.");
        }
    }

  return (
    <div className="flex min-h-screen items-center justify-center bg-slate-50 px-4">
      {/* Background Accent (Matches Sidebar/Topbar blue) */}
      <div className="absolute top-0 left-0 h-1 w-full bg-blue-600" />

      <div className="w-full max-w-md space-y-8 rounded-2xl border border-slate-200 bg-white p-10 shadow-sm">
        {/* Header Section */}
        <div className="text-center">
          <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-600 text-white shadow-lg shadow-blue-200">
            <span className="text-2xl font-bold">B</span>
          </div>
          <h1 className="text-2xl font-bold tracking-tight text-slate-900">Welcome Back</h1>
          <p className="mt-2 text-sm text-slate-500">Please enter your credentials to log in.</p>
        </div>

        {/* Form Section */}
        <div className="space-y-5">
          {/* Email Input */}
          <div className="space-y-1.5">
            <label className="text-sm font-medium text-slate-700">Email Address</label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Mail size={18} />
              </div>
              <input 
                type="email" 
                name="email" 
                placeholder='name@company.com'
                className="w-full rounded-lg border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-sm text-slate-900 transition-all focus:border-blue-600 focus:bg-white focus:ring-4 focus:ring-blue-100 outline-none"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
          </div>

          {/* Password Input */}
          <div className="space-y-1.5">
            <div className="flex justify-between items-center">
              <label className="text-sm font-medium text-slate-700">Password</label>
              <button className="text-xs font-semibold text-blue-600 hover:text-blue-700">Forgot?</button>
            </div>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Lock size={18} />
              </div>
              <input 
                type="password" 
                name="password" 
                placeholder='••••••••'
                className="w-full rounded-lg border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-sm text-slate-900 transition-all focus:border-blue-600 focus:bg-white focus:ring-4 focus:ring-blue-100 outline-none"
                value={password}
                onChange={e => setPassword(e.target.value)}
              />
            </div>
          </div>

          {/* Login Button */}
          <button 
            className="flex w-full items-center justify-center gap-2 rounded-lg bg-blue-600 py-3 text-sm font-semibold text-white transition-all hover:bg-blue-700 active:scale-[0.98]"
            onClick={handleLogin}
          >
            <LogIn size={18} />
            Sign In
          </button>
        </div>

        {/* Footer */}
        <p className="text-center text-sm text-slate-500">
          New here? <button className="font-semibold text-blue-600 hover:underline">Create an account</button>
        </p>
      </div>
    </div>
  );
}

export default LoginPage