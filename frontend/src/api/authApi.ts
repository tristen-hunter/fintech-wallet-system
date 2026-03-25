import api from "./axios.ts";

export interface LoginCredentials {
    email: string,
    password: string
}

export interface SignupCredentials {
    userName: string,
    email: string,
    password: string
}

export const login = (data: LoginCredentials) => {
    return api.post("/auth/login", data);
}

export const signup = (data: SignupCredentials) => {
    return api.post("/auth/signup", data);
}