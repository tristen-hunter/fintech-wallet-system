import { useState } from 'react'

const SignupPage = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

  return (
    <>
        <h1>Sign Up page</h1>
        <input 
            type="username" 
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
            onClick={() => console.log(username, email, password)}
        >
            Sign Up
        </button>
    </>
  )
}

export default SignupPage