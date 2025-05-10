import React, { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { addDeviceToHouse } from '../api/DeviceApi';
import '../styles/global.css';

function AddDeviceForm() {
  const { houseId } = useParams(); // Получаем ID дома из URL
  const navigate = useNavigate();

  // Состояния для полей формы
  const [deviceProfile, setDeviceProfile] = useState(''); // Тип устройства
  const [name, setName] = useState(''); // Название устройства
  const [power, setPower] = useState(''); // Мощность (Вт)
  const [count, setCount] = useState(''); // Количество устройств
  const [averageDailyUsageMinutes, setAverageDailyUsageMinutes] = useState(''); // Время работы в день (минуты)
  const [energyEfficiency, setEnergyEfficiency] = useState(''); // Энергоэффективность
  const [usageTimePeriod, setUsageTimePeriod] = useState(''); // Время использования

  // Перечисления для выпадающих списков
  const deviceProfiles = ['REFRIGERATOR', 'HAIR_DRYER', 'TELEVISION', 'AIR_CONDITIONER'];
  const energyEfficiencies = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
  const usageTimePeriods = ['DAY_ONLY', 'NIGHT_ONLY', 'BOTH_DAY_NIGHT'];

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const deviceData = {
        deviceProfile,
        name,
        power: parseInt(power, 10),
        count: parseInt(count, 10),
        averageDailyUsageMinutes: parseInt(averageDailyUsageMinutes, 10),
        energyEfficiency,
        usageTimePeriod,
      };

      await addDeviceToHouse(houseId, deviceData); // Отправляем данные на сервер
      alert('Устройство успешно добавлено!');
      navigate(`/house/${houseId}`); // Возвращаемся на страницу дома
    } catch (error) {
      console.error('Ошибка при добавлении устройства:', error);
      alert('Не удалось добавить устройство. Попробуйте снова.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Добавить устройство</h1>
      <form className="form" onSubmit={handleSubmit}>
        {/* Тип устройства */}
        <label>
          Тип устройства:
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
        </label>

        {/* Название устройства */}
        <input
          type="text"
          placeholder="Название устройства"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

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

        {/* Время работы в день */}
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
        <label>
          Время использования:
          <select
            value={usageTimePeriod}
            onChange={(e) => setUsageTimePeriod(e.target.value)}
            required
          >
            <option value="">Выберите время использования</option>
            {usageTimePeriods.map((period) => (
              <option key={period} value={period}>
                {period}
              </option>
            ))}
          </select>
        </label>

        {/* Кнопка отправки */}
        <button type="submit">Добавить устройство</button>
      </form>
    </div>
  );
}

export default AddDeviceForm;