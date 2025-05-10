import apiClient from './apiClient';

// Получить список отчетов для дома
export const getReportsByHouseId = (houseId) => {
  return apiClient.get(`/users/${localStorage.getItem('userId')}/houses/${houseId}/reports`);
};

// Создать новый отчет для дома
export const createReportForHouse = (houseId) => {
  return apiClient.post(`/users/${localStorage.getItem('userId')}/houses/${houseId}/reports/new-report`);
};

// Удалить отчет по ID
export const deleteReportById = (reportId) => {
  return apiClient.delete(`/users/${localStorage.getItem('userId')}/houses/reports/${reportId}`);
};

// Получить отчет по ID
export const getReportById = (reportId) => {
  return apiClient.get(`/users/${localStorage.getItem('userId')}/houses/${localStorage.getItem('houseId')}/reports/${reportId}`);
};

// Удалить отчет по ID
export const deleteReport = (reportId) => {
  return apiClient.delete(`/users/${localStorage.getItem('userId')}/houses/${localStorage.getItem('houseId')}/reports/${reportId}`);
};