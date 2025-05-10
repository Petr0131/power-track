import apiClient from './apiClient';

export const getHouses = (userId) => {
  return apiClient.get(`/users/${userId}/houses`);
};

export const addHouse = (userId, houseData) => {
  return apiClient.post(`/users/${userId}/houses`, houseData);
};

// Получить информацию о доме по ID
export const getHouseById = (houseId) => {
  return apiClient.get(`/users/${localStorage.getItem('userId')}/houses/${houseId}`);
};

// Удалить дом по ID
export const deleteHouseById = (houseId) => {
  return apiClient.delete(`/users/${localStorage.getItem('userId')}/houses/${houseId}`);
};

// Частично обновить данные дома
export const patchHouse = (houseId, updatedData) => {
  return apiClient.patch(`/users/${localStorage.getItem('userId')}/houses/${houseId}`, updatedData);
};