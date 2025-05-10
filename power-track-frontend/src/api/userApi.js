import apiClient from './apiClient';

// Получить пользователя по имени
export const getUserByUsername = (username) => {
  return apiClient.get(`/users/${username}`);
};

// Обновить данные пользователя
export const updateUser = (username, updatedData) => {
  return apiClient.patch(`/users/${username}`, updatedData);
};

// Частично обновить данные пользователя
export const patchUser = (username, updatedData) => {
  return apiClient.patch(`/users/${username}`, updatedData);
};