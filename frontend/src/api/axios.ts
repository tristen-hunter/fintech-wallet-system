import axios from "axios";

// api makes it easier to call my api (not neccessary but makes code easier to write / read)
const api = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,
})

export default api;