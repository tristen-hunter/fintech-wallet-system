import { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import type UserfetchDTO from "../types/UserFetchDTO";
import api from "../api/axios";

// This is an interface of interfaces and functions, they are stored in the context to be accessed at anypoint
    // This is regularly used / needed data so It is added to the context layer
interface AuthContextType {
    user: UserfetchDTO | null;
    loading: boolean,
    loginUser: (userData: UserfetchDTO) => void;
    logoutUser: () => void;
}

// Creates the context to be accessed
const AuthContext = createContext<AuthContextType | undefined>  (undefined);

export const AuthProvider = ({ children }: { children: ReactNode}) => {
    // create states for the user and the loading status
    const [user, setUser] = useState<UserfetchDTO | null>(null);
    const [loading, setLoading] =useState(true);


    // useEffect for a session mount (if backend finds a session return the DTO)
    useEffect(() => {
        api.get("/auth/me")
        .then(result => {
            // if a session is found by the backend, it returns the UserfetchDTO
            setUser(result.data);
        })
        .catch(() => {
            // If no session or their was an error the user remains null
            setUser(null);
        })
        .finally(() => {
            setLoading(false);
        });
    }, [])


    // Manual login (Called by LoginPage)
    const loginUser = (userData: UserfetchDTO) => {
        setUser(userData)
    }

    // optionally implement a logout helper
    const logoutUser = () => {
        setUser(null);
        // Need to call backend Auth to logout here too
    }

    return (
        <AuthContext.Provider value={{ user, loading, loginUser, logoutUser }}>
            {children}
        </AuthContext.Provider>
    )
}

// Custom hook to receive the authentication confirmation
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an Authprovider");
    }
    return context;
};