import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getReportById, deleteReport } from '../api/ReportApi';
import { getRecommendationsForReport } from '../api/recommendationApi';
import '../styles/global.css';

function ReportDetailsPage() {
  const { reportId, houseId } = useParams(); // Получаем ID отчета и дома из URL
  const navigate = useNavigate();

  // Состояния для данных отчета и рекомендаций
  const [report, setReport] = useState(null);
  const [recommendations, setRecommendations] = useState([]);

  useEffect(() => {
    // Загружаем данные отчета
    const fetchReportData = async () => {
      try {
        const response = await getReportById(reportId);
        setReport(response.data.data); // Сохраняем данные отчета
      } catch (error) {
        console.error('Ошибка при загрузке данных отчета:', error);
      }
    };

    // Загружаем рекомендации для отчета
    const fetchRecommendations = async () => {
      try {
        const response = await getRecommendationsForReport(reportId);
        const sortedRecommendations = response.data.data.sort(
          (a, b) => a.priority.localeCompare(b.priority) // Сортировка по приоритету
        );
        setRecommendations(sortedRecommendations);
      } catch (error) {
        console.error('Ошибка при загрузке рекомендаций:', error);
      }
    };

    fetchReportData();
    fetchRecommendations();
  }, [reportId]);

  const handleBack = () => {
    navigate(`/house/${houseId}`); // Возвращаемся на страницу дома
  };

  const handleDeleteReport = async () => {
    const confirmDelete = window.confirm(
      'Вы уверены, что хотите удалить этот отчет? Это действие нельзя отменить.'
    );
    if (confirmDelete) {
      try {
        await deleteReport(reportId); // Удаляем отчет
        navigate(`/house/${houseId}`); // Возвращаемся на страницу дома
      } catch (error) {
        console.error('Ошибка при удалении отчета:', error);
        alert('Не удалось удалить отчет. Попробуйте снова.');
      }
    }
  };

  const getPriorityClass = (priority) => {
    switch (priority) {
      case 'HIGH':
        return 'high-priority';
      case 'MEDIUM':
        return 'medium-priority';
      case 'LOW':
        return 'low-priority';
      default:
        return '';
    }
  };

  if (!report) {
    return <div className="container">Загрузка...</div>;
  }

  return (
    <div className="container">
      <div className="house-page-box">
        {/* Заголовок */}
        <div className="house-title">
          <h1>Отчет за период: {report.startDate} - {report.endDate}</h1>
        </div>

        {/* Основной контейнер */}
        <div className="house-content">
          {/* Левая колонка */}
          <div className="left-column">
            {/* Информация об отчете */}
            <div className="info-section">
              <h2>Информация об отчете</h2>
              <div className="house-info">
                <p>Общее потребление: {report.totalConsumption.toFixed(2)} кВт⋅ч</p>
                <p>Общая стоимость: {report.totalCost.toFixed(2)} ₽</p>
                <p>Дата создания: {new Date().toLocaleDateString()}</p>
              </div>
            </div>
          </div>

          {/* Правая колонка */}
          <div className="right-column">
            {/* Рекомендации */}
            <div className="devices-section">
              <h2>Рекомендации</h2>
              {recommendations.length > 0 ? (
                <div className="recommendations-list">
                  {recommendations.map((recommendation) => (
                    <div
                      key={recommendation.id}
                      className={`recommendation-card ${getPriorityClass(recommendation.priority)}`}
                    >
                      <p>{recommendation.message}</p>
                      <p>
                        Потенциальная экономия: {recommendation.potentialSavings.toFixed(2)} ₽/месяц
                      </p>
                      <p>Приоритет: {recommendation.priority}</p>
                    </div>
                  ))}
                </div>
              ) : (
                <p>Нет доступных рекомендаций.</p>
              )}
            </div>
          </div>
        </div>

        {/* Нижняя секция с кнопками */}
        <div className="button-group">
          <button className="house-button">Подготовить PDF</button>
          <button className="house-button">Сгенерировать графики</button>
          <button className="house-button danger" onClick={handleDeleteReport}>
            Удалить отчет
          </button>
          <button className="house-button" onClick={handleBack}>
            Назад
          </button>
        </div>
      </div>
    </div>
  );
}

export default ReportDetailsPage;