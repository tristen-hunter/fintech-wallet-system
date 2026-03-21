import { useState } from 'react'
import { signup } from '../api/authApi';

const SignupPage = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    // Send Data to the backend
        const handleSignup = async () => {
            try {
                const response = await signup({ username, email, password })
    
                console.log("User: ", response.data)
            } catch (error) {
                console.error("Sign Up failed:", error);
            }
        }

  return (
    <>
        <h1>Sign Up page</h1>
        <input 
            type="text" 
            name="username" 
            placeholder='username'
            className="border p-2 block mb-2"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
        />
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
            onClick={handleSignup}
        >
            Sign Up
        </button>
    </>
  )
}

export default SignupPage