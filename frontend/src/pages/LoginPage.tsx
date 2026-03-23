import { useState } from 'react'
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
    <>
    <h1>Login page</h1>
    <input 
        type="email" 
        name="email" 
        placeholder='email'
        className="border p-2 block mb-2"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
    />
    <input 
        type="password" 
        name="password" 
        placeholder='password..'
        className="border p-2 block mb-2"
        value={password}
        onChange={e => setPassword(e.target.value)}
    />
    <button 
        className="bg-blue-500 text-white p-2"
        onClick={handleLogin}
    >
        login
    </button>
    </>
  )
}

export default LoginPage