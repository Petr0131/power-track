import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getDeviceById, deleteDevice } from '../api/DeviceApi';
import '../styles/global.css';

function DeviceDetailsPage() {
  const { deviceId, houseId } = useParams(); // Получаем ID устройства и дома из URL
  const navigate = useNavigate();

  // Состояние для данных устройства
  const [device, setDevice] = useState(null);

  useEffect(() => {
    // Загружаем данные устройства
    const fetchDeviceData = async () => {
      try {
        const response = await getDeviceById(deviceId);
        setDevice(response.data.data); // Сохраняем данные устройства
      } catch (error) {
        console.error('Ошибка при загрузке данных устройства:', error);
      }
    };

    fetchDeviceData();
  }, [deviceId]);

  const handleBack = () => {
    navigate(`/house/${houseId}`); // Возвращаемся на страницу дома
  };

  const handleEditDevice = () => {
    navigate(`/edit-device/${houseId}/${deviceId}`); // Переходим на страницу редактирования устройства
  };

  const handleDeleteDevice = async () => {
    const confirmDelete = window.confirm(
      'Вы уверены, что хотите удалить это устройство? Это действие нельзя отменить.'
    );
    if (confirmDelete) {
      try {
        await deleteDevice(houseId, deviceId); // Удаляем устройство
        navigate(`/house/${houseId}`); // Возвращаемся на страницу дома
      } catch (error) {
        console.error('Ошибка при удалении устройства:', error);
        alert('Не удалось удалить устройство. Попробуйте снова.');
      }
    }
  };

  if (!device) {
    return <div className="container">Загрузка...</div>;
  }

  return (
    <div className="container">
      <div className="house-page-box">
        {/* Заголовок */}
        <div className="house-title">
          <h1>{device.name}</h1>
        </div>

        {/* Основной контейнер */}
        <div className="house-details-content">
          {/* Информация об устройстве */}
          <div className="info-section">
            <h2>Информация об устройстве</h2>
            <div className="house-info">
              <p>Тип устройства: {device.deviceProfile}</p>
              <p>Мощность: {device.power} Вт</p>
              <p>Количество устройств: {device.count}</p>
              <p>Среднее время работы в день: {device.averageDailyUsageMinutes} минут</p>
              <p>Энергоэффективность: {device.energyEfficiency}</p>
              <p>Время использования: {device.usageTimePeriod}</p>
            </div>
          </div>
        </div>

        {/* Нижняя секция с кнопками */}
        <div className="button-group">
          <button className="house-button" onClick={handleEditDevice}>
            Редактировать устройство
          </button>
          <button className="house-button danger" onClick={handleDeleteDevice}>
            Удалить устройство
          </button>
          <button className="house-button" onClick={handleBack}>
            Назад
          </button>
        </div>
      </div>
    </div>
  );
}

export default DeviceDetailsPage;