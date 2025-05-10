import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getDeviceById, patchDevice } from '../api/DeviceApi';
import '../styles/global.css';

function EditDeviceForm() {
  const { deviceId, houseId } = useParams(); // Получаем ID устройства и дома из URL
  const navigate = useNavigate();

  // Состояния для полей формы
  const [name, setName] = useState('');
  const [deviceProfile, setDeviceProfile] = useState('');
  const [power, setPower] = useState('');
  const [count, setCount] = useState('');
  const [averageDailyUsageMinutes, setAverageDailyUsageMinutes] = useState('');
  const [energyEfficiency, setEnergyEfficiency] = useState('');
  const [usageTimePeriod, setUsageTimePeriod] = useState('');

  // Перечисления для выпадающих списков
  const deviceProfiles = ['REFRIGERATOR', 'HAIR_DRYER', 'TELEVISION', 'AIR_CONDITIONER'];
  const energyEfficiencies = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
  const usageTimePeriods = ['DAY_ONLY', 'NIGHT_ONLY', 'BOTH_DAY_NIGHT'];

  // Загрузка данных устройства при монтировании компонента
  useEffect(() => {
    const fetchDeviceData = async () => {
      try {
        const response = await getDeviceById(deviceId);
        const device = response.data.data;

        // Заполняем поля формы данными устройства
        setName(device.name);
        setDeviceProfile(device.deviceProfile);
        setPower(device.power);
        setCount(device.count);
        setAverageDailyUsageMinutes(device.averageDailyUsageMinutes);
        setEnergyEfficiency(device.energyEfficiency);
        setUsageTimePeriod(device.usageTimePeriod);
      } catch (error) {
        console.error('Ошибка при загрузке данных устройства:', error);
        alert('Не удалось загрузить данные устройства.');
      }
    };

    fetchDeviceData();
  }, [deviceId]);

  // Обработчик отправки формы
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Создаём объект с изменёнными данными
      const updatedDeviceData = {};
      if (name !== '') updatedDeviceData.name = name;
      if (deviceProfile !== '') updatedDeviceData.deviceProfile = deviceProfile;
      if (power !== '') updatedDeviceData.power = parseInt(power, 10);
      if (count !== '') updatedDeviceData.count = parseInt(count, 10);
      if (averageDailyUsageMinutes !== '')
        updatedDeviceData.averageDailyUsageMinutes = parseInt(averageDailyUsageMinutes, 10);
      if (energyEfficiency !== '') updatedDeviceData.energyEfficiency = energyEfficiency;
      if (usageTimePeriod !== '') updatedDeviceData.usageTimePeriod = usageTimePeriod;

      // Если нет изменений, выходим
      if (Object.keys(updatedDeviceData).length === 0) {
        alert('Нет изменений для сохранения.');
        return;
      }

      // Отправляем PATCH-запрос
      await patchDevice(deviceId, updatedDeviceData);

      alert('Устройство успешно обновлено!');
      navigate(`/house/${houseId}`); // Перенаправляем на страницу дома
    } catch (error) {
      console.error('Ошибка при обновлении устройства:', error);
      alert('Не удалось обновить устройство.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Редактировать устройство</h1>
      <form className="form" onSubmit={handleSubmit}>
        {/* Название устройства */}
        <input
          type="text"
          placeholder="Название устройства"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        {/* Тип устройства */}
        <select
          value={deviceProfile}
          onChange={(e) => setDeviceProfile(e.target.value)}
          required
        >
          <option value="">Выберите тип устройства</option>
          {deviceProfiles.map((profile) => (
            <option key={profile} value={profile}>
              {profile}
            </option>
          ))}
        </select>

        {/* Мощность */}
        <input
          type="number"
          placeholder="Мощность (Вт)"
          value={power}
          onChange={(e) => setPower(e.target.value)}
          required
        />

        {/* Количество устройств */}
        <input
          type="number"
          placeholder="Количество устройств"
          value={count}
          onChange={(e) => setCount(e.target.value)}
          required
        />

        {/* Среднее время работы в день */}
        <input
          type="number"
          placeholder="Среднее время работы в день (минуты)"
          value={averageDailyUsageMinutes}
          onChange={(e) => setAverageDailyUsageMinutes(e.target.value)}
          required
        />

        {/* Энергоэффективность */}
        <select
          value={energyEfficiency}
          onChange={(e) => setEnergyEfficiency(e.target.value)}
          required
        >
          <option value="">Выберите класс энергоэффективности</option>
          {energyEfficiencies.map((efficiency) => (
            <option key={efficiency} value={efficiency}>
              {efficiency}
            </option>
          ))}
        </select>

        {/* Время использования */}
        <select
          value={usageTimePeriod}
          onChange={(e) => setUsageTimePeriod(e.target.value)}
          required
        >
          <option value="">Выберите период использования</option>
          {usageTimePeriods.map((period) => (
            <option key={period} value={period}>
              {period}
            </option>
          ))}
        </select>

        {/* Кнопка отправки */}
        <button type="submit">Сохранить изменения</button>
      </form>
      <button className="custom-back-button" onClick={() => navigate('/dashboard')}>Назад</button>
    </div>
  );
}

export default EditDeviceForm;