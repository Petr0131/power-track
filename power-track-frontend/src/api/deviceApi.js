import apiClient from './apiClient';

// Получить список устройств для дома
export const getDevicesByHouseId = (houseId) => {
  return apiClient.get(`/users/${localStorage.getItem('userId')}/houses/${houseId}/devices`);
};

// Добавить устройство в дом
export const addDeviceToHouse = (houseId, deviceData) => {
  return apiClient.post(`/users/${localStorage.getItem('userId')}/houses/${houseId}/devices`, deviceData);
};

// Получить устройство по ID
export const getDeviceById = (deviceId) => {
  return apiClient.get(`/users/${localStorage.getItem('userId')}/houses/${localStorage.getItem('houseId')}/devices/${deviceId}`);
};

// Удалить устройство по ID
export const deleteDevice = (houseId, deviceId) => {
  return apiClient.delete(`/users/${localStorage.getItem('userId')}/houses/${houseId}/devices/${deviceId}`);
};

// Обновить устройство
export const updateDevice = (deviceId, updatedData) => {
  return apiClient.put(`/users/${localStorage.getItem('userId')}/houses/${localStorage.getItem('houseId')}/devices/${deviceId}`, updatedData);
};

// Обновить устройство (частичное обновление)
export const patchDevice = (deviceId, updatedData) => {
  return apiClient.patch(`/users/${localStorage.getItem('userId')}/houses/${localStorage.getItem('houseId')}/devices/${deviceId}`, updatedData);
};