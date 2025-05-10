import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getHouseById, patchHouse } from '../api/houseApi';
import '../styles/global.css';

function EditHouseForm() {
  const { houseId } = useParams(); // Получаем ID дома из URL
  const navigate = useNavigate();

  // Состояния для полей формы
  const [name, setName] = useState('');
  const [rooms, setRooms] = useState('');
  const [residents, setResidents] = useState('');
  const [dayTariff, setDayTariff] = useState('');
  const [nightTariff, setNightTariff] = useState('');

  // Загрузка данных дома при монтировании компонента
  useEffect(() => {
    const fetchHouseData = async () => {
      try {
        const response = await getHouseById(houseId);
        const house = response.data.data;

        // Заполняем поля формы данными дома
        setName(house.name);
        setRooms(house.rooms);
        setResidents(house.residents);
        setDayTariff(house.dayTariff);
        setNightTariff(house.nightTariff);
      } catch (error) {
        console.error('Ошибка при загрузке данных дома:', error);
        alert('Не удалось загрузить данные дома.');
      }
    };

    fetchHouseData();
  }, [houseId]);

  // Обработчик отправки формы
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Создаём объект с изменёнными данными
      const updatedHouseData = {};
      if (name !== '') updatedHouseData.name = name;
      if (rooms !== '') updatedHouseData.rooms = parseInt(rooms, 10);
      if (residents !== '') updatedHouseData.residents = parseInt(residents, 10);
      if (dayTariff !== '') updatedHouseData.dayTariff = parseFloat(dayTariff);
      if (nightTariff !== '') updatedHouseData.nightTariff = parseFloat(nightTariff);

      // Если нет изменений, выходим
      if (Object.keys(updatedHouseData).length === 0) {
        alert('Нет изменений для сохранения.');
        return;
      }

      // Отправляем PATCH-запрос
      await patchHouse(houseId, updatedHouseData);

      alert('Дом успешно обновлён!');
      navigate(`/house/${houseId}`); // Перенаправляем на страницу дома
    } catch (error) {
      console.error('Ошибка при обновлении дома:', error);
      alert('Не удалось обновить дом.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Редактировать дом</h1>
      <form className="form" onSubmit={handleSubmit}>
        {/* Название дома */}
        <input
          type="text"
          placeholder="Название дома"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />

        {/* Количество комнат */}
        <input
          type="number"
          placeholder="Количество комнат"
          value={rooms}
          onChange={(e) => setRooms(e.target.value)}
          required
        />

        {/* Количество жильцов */}
        <input
          type="number"
          placeholder="Количество жильцов"
          value={residents}
          onChange={(e) => setResidents(e.target.value)}
          required
        />

        {/* Дневной тариф */}
        <input
          type="number"
          step="0.01"
          placeholder="Дневной тариф (₽/кВт⋅ч)"
          value={dayTariff}
          onChange={(e) => setDayTariff(e.target.value)}
          required
        />

        {/* Ночной тариф */}
        <input
          type="number"
          step="0.01"
          placeholder="Ночной тариф (₽/кВт⋅ч)"
          value={nightTariff}
          onChange={(e) => setNightTariff(e.target.value)}
          required
        />

        {/* Кнопка отправки */}
        <button type="submit">Сохранить изменения</button>
      </form>
      <button className="custom-back-button" onClick={() => navigate('/dashboard')}>Назад</button>
    </div>
  );
}

export default EditHouseForm;