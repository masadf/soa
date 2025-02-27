import axios from "axios";

export const api = axios.create({
    baseURL: "https://localhost:8443/marines",
});

export const apiShips = axios.create({
    baseURL: "https://localhost:8081/starships",
});