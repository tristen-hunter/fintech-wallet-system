import api from "./axios.ts";


// Defines the shape of the login credentials expected by the backend
export interface LoginCredentials {
    email: string,
    password: string
}

// Defines the shape of the signup credentials expected by the backend
export interface SignupCredentials {
    userName: string,
    email: string,
    password: string
}

//// Calls Login API and send the data from the frontend
export const login = (data: LoginCredentials) => {
    return api.post("/auth/login", data);
}

export const signup = (data: SignupCredentials) => {
    return api.post("/auth/signup", data);
}