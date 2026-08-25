import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/"
});

// Interceptor para injetar o Token JWT automaticamente em endpoints protegidos
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('@ReserveSpace:token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;