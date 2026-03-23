import { Outlet, Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const ProtectedRoutes = () => {
    // useAuth custom hook
    const { user, loading } = useAuth()

    // While waiting for /auth/me to respond, show nothing or a spinner
    if (loading){
        return(
            <div className="flex h-screen items-center justify-center">
                <p className="text-slate-500">Verifying session...</p>
            </div>
        )
    }

    // If there is a user (UserfetchDTO), allow access to the Outlet (Dashboard, Wallets etc.)
    // Otherwise, redirect them to the login page
    return user ? <Outlet /> : <Navigate to="/login" />
}

export default ProtectedRoutes;