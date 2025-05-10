import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getHouseById, deleteHouseById } from '../api/houseApi';
import { getDevicesByHouseId } from '../api/DeviceApi';
import { getReportsByHouseId, createReportForHouse } from '../api/ReportApi';
import '../styles/global.css';

function HousePage() {
  const { houseId } = useParams(); // Получаем ID дома из URL
  const navigate = useNavigate();

  // Состояния для данных дома, устройств и отчетов
  const [house, setHouse] = useState(null);
  const [devices, setDevices] = useState([]);
  const [reports, setReports] = useState([]);

  useEffect(() => {
    // Загружаем данные дома
    const fetchHouseData = async () => {
      try {
        const response = await getHouseById(houseId);
        setHouse(response.data.data); // Сохраняем данные дома
      } catch (error) {
        console.error('Ошибка при загрузке данных дома:', error);
      }
    };

    // Загружаем устройства дома
    const fetchDevices = async () => {
      try {
        const response = await getDevicesByHouseId(houseId);
        setDevices(response.data.data); // Сохраняем список устройств
      } catch (error) {
        console.error('Ошибка при загрузке устройств:', error);
      }
    };

    // Загружаем отчеты дома
    const fetchReports = async () => {
      try {
        const response = await getReportsByHouseId(houseId);
        setReports(response.data.data); // Сохраняем список отчетов
      } catch (error) {
        console.error('Ошибка при загрузке отчетов:', error);
      }
    };

    fetchHouseData();
    fetchDevices();
    fetchReports();
  }, [houseId]);

  const handleBack = () => {
    navigate('/dashboard'); // Возвращаемся на страницу дашбоарда
  };

  const handleDeleteHouse = async () => {
    const confirmDelete = window.confirm(
      'Вы уверены, что хотите удалить этот дом? Это действие нельзя отменить.'
    );
    if (confirmDelete) {
      try {
        await deleteHouseById(houseId); // Удаляем дом
        navigate('/dashboard'); // Возвращаемся на дашбоард
      } catch (error) {
        console.error('Ошибка при удалении дома:', error);
        alert('Не удалось удалить дом. Попробуйте снова.');
      }
    }
  };

  const handleGenerateReport = async () => {
    try {
      const response = await createReportForHouse(houseId); // Создаем новый отчет
      setReports((prevReports) => [...prevReports, response.data.data]); // Добавляем отчет в список
      alert('Отчет успешно создан!');
    } catch (error) {
      console.error('Ошибка при создании отчета:', error);
      alert('Не удалось создать отчет. Попробуйте снова.');
    }
  };

  if (!house) {
    return <div className="container">Загрузка...</div>;
  }

  return (
    <div className="container">
      <div className="house-page-box">
        {/* Заголовок */}
        <div className="house-title">
          <h1>{house.name}</h1>
        </div>

        {/* Основной контейнер */}
        <div className="house-content">
          {/* Левая колонка */}
          <div className="left-column">
            {/* Информация о доме */}
            <div className="info-section">
              <h2>Информация о доме</h2>
              <div className="house-info">
                <p>Количество комнат: {house.rooms}</p>
                <p>Количество жильцов: {house.residents}</p>
                <p>Дневной тариф: {house.dayTariff} ₽/кВт⋅ч</p>
                <p>Ночной тариф: {house.nightTariff} ₽/кВт⋅ч</p>
              </div>
            </div>

            {/* Отчеты */}
            <div className="reports-section">
              <h2>Отчеты</h2>
              {reports.length > 0 ? (
                <div className="reports-list">
                  {reports.map((report) => (
                    <div key={report.id} className="report-card" onClick={() => navigate(`/report/${houseId}/${report.id}`)}>
                      <p>Отчет за период: {report.startDate} - {report.endDate}</p>
                      <p>Общее потребление: {report.totalConsumption} кВт⋅ч</p>
                      <p>Стоимость: {report.totalCost} ₽</p>
                    </div>
                  ))}
                </div>
              ) : (
                <p>Нет доступных отчетов.</p>
              )}
            </div>
          </div>

          {/* Правая колонка */}
          <div className="right-column">
            {/* Устройства */}
            <div className="devices-section">
              <h2>Устройства</h2>
              {devices.length > 0 ? (
                <div className="devices-list">
                  {devices.map((device) => (
                    <div key={device.id} className="device-card" onClick={() => navigate(`/device/${houseId}/${device.id}`)}>
                      <p>{device.name}</p>
                      <p>Мощность: {device.power} Вт</p>
                      <p>Среднее время работы: {device.averageDailyUsageMinutes} мин/день</p>
                    </div>
                  ))}
                </div>
              ) : (
                <p>Нет добавленных устройств.</p>
              )}
            </div>
          </div>
        </div>

        {/* Нижняя секция с кнопками */}
        <div className="button-group">
          <button className="house-button" onClick={() => navigate(`/add-device/${houseId}`)}>Добавить устройство</button>
          <button className="house-button" onClick={handleGenerateReport}>Сгенерировать отчет</button>
          <button className="house-button" onClick={() => navigate(`/edit-house/${houseId}`)}>Редактировать дом</button>
          <button className="house-button danger" onClick={handleDeleteHouse}>Удалить дом</button>
          <button className="house-button" onClick={handleBack}>Назад</button>
        </div>
      </div>
    </div>
  );
}

export default HousePage;