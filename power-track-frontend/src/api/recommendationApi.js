import apiClient from './apiClient';

// Получить рекомендации для отчета
export const getRecommendationsForReport = (reportId) => {
  return apiClient.get(`/recommendation/${reportId}`);
};