import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080', // URL вашего сервера
  headers: {
    'Content-Type': 'application/json',
  },
});

// Добавляем JWT-токен в заголовки запросов
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default apiClient;