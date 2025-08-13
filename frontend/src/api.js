import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    console.log(
      "API 요청:",
      config.method?.toUpperCase(),
      config.url,
      config.data
    );
    return config;
  },
  (error) => {
    console.error("API 요청 에러:", error);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    console.log("API 응답:", response.status, response.data);
    return response;
  },
  (error) => {
    console.error(
      "API 응답 에러:",
      error.response?.status,
      error.response?.data
    );
    return Promise.reject(error);
  }
);

export const todoApi = {
  getAllTodos: async (params = {}) => {
    const response = await api.get("/todos", { params });
    return response.data;
  },
};
