import { useState } from 'react'
import { login } from "../api/authApi.ts";


const LoginPage = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");


    // Send Data to the backend
    const handleLogin = async () => {
        try {
            const response = await login({ email, password })

            console.log("User: ", response.data)
        } catch (error) {
            console.error("Login failed:", error);
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