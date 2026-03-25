import { useState } from 'react';
import { signup } from '../api/authApi';
import { User, Mail, Lock, UserPlus, ArrowRight } from "lucide-react";
import { Link } from "react-router-dom";
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';


const SignupPage = () => {
  const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { loginUser } = useAuth();
  const navigate = useNavigate();

  const handleSignup = async (e: React.SubmitEvent) => {
    e.preventDefault(); // Prevents page reload
    try {
      const response = await signup({ userName, email, password });
      console.log("User: ", response.data);

      const userData = response.data;
      loginUser(userData);

      navigate("/dashboard")
    } catch (error) {
      console.error("Sign Up failed:", error);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-slate-50 px-4 py-12">
      {/* Top Accent Bar */}
      <div className="absolute top-0 left-0 h-1 w-full bg-blue-600" />

      <div className="w-full max-w-md space-y-8 rounded-2xl border border-slate-200 bg-white p-10 shadow-sm">
        {/* Header */}
        <div className="text-center">
          <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-blue-600 text-white shadow-lg shadow-blue-200">
            <span className="text-2xl font-bold">B</span>
          </div>
          <h1 className="text-2xl font-bold tracking-tight text-slate-900">Create an account</h1>
          <p className="mt-2 text-sm text-slate-500">Join BlueBank and start managing your wallets.</p>
        </div>

        {/* Form */}
        <form onSubmit={handleSignup} className="space-y-5">
          {/* Username Input */}
          <div className="space-y-1.5">
            <label className="text-sm font-medium text-slate-700">Username</label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <User size={18} />
              </div>
              <input 
                type="text" 
                placeholder='johndoe'
                className="w-full rounded-lg border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-sm text-slate-900 transition-all focus:border-blue-600 focus:bg-white focus:ring-4 focus:ring-blue-100 outline-none"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                required
              />
            </div>
          </div>

          {/* Email Input */}
          <div className="space-y-1.5">
            <label className="text-sm font-medium text-slate-700">Email Address</label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Mail size={18} />
              </div>
              <input 
                type="email" 
                placeholder='name@company.com'
                className="w-full rounded-lg border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-sm text-slate-900 transition-all focus:border-blue-600 focus:bg-white focus:ring-4 focus:ring-blue-100 outline-none"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
          </div>

          {/* Password Input */}
          <div className="space-y-1.5">
            <label className="text-sm font-medium text-slate-700">Password</label>
            <div className="relative">
              <div className="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Lock size={18} />
              </div>
              <input 
                type="password" 
                placeholder='••••••••'
                className="w-full rounded-lg border border-slate-200 bg-slate-50 py-2.5 pl-10 pr-3 text-sm text-slate-900 transition-all focus:border-blue-600 focus:bg-white focus:ring-4 focus:ring-blue-100 outline-none"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
              />
            </div>
          </div>

          {/* Terms & Conditions (Optional but standard) */}
          <p className="text-[11px] text-slate-500 text-center">
            By signing up, you agree to our <span className="text-blue-600 cursor-pointer">Terms of Service</span> and <span className="text-blue-600 cursor-pointer">Privacy Policy</span>.
          </p>

          {/* Signup Button */}
          <button 
            type="submit"
            className="flex w-full items-center justify-center gap-2 rounded-lg bg-blue-600 py-3 text-sm font-semibold text-white transition-all hover:bg-blue-700 hover:shadow-lg hover:shadow-blue-200 active:scale-[0.98]"
          >
            <UserPlus size={18} />
            Create Account
          </button>
        </form>

        {/* Footer */}
        <p className="text-center text-sm text-slate-500">
          Already have an account?{" "}
          <Link to="/login" className="font-semibold text-blue-600 hover:text-blue-700 hover:underline">
            Log in
          </Link>
        </p>
      </div>
    </div>
  );
};

export default SignupPage;