import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addHouse } from '../api/houseApi';
import '../styles/global.css';

function AddHouseForm() {
  const [name, setName] = useState('');
  const [rooms, setRooms] = useState('');
  const [residents, setResidents] = useState('');
  const [dayTariff, setDayTariff] = useState('');
  const [nightTariff, setNightTariff] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        alert('Пользователь не авторизован.');
        return;
      }

      const houseData = {
        name,
        rooms: parseInt(rooms, 10),
        residents: parseInt(residents, 10),
        dayTariff: parseFloat(dayTariff),
        nightTariff: parseFloat(nightTariff),
      };

      await addHouse(userId, houseData);
      alert('Дом успешно добавлен!');
      navigate('/dashboard'); // Перенаправляем на Dashboard
    } catch (error) {
      console.error('Error adding house:', error);
      alert('Ошибка при добавлении дома.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Добавить дом</h1>
      <form className="form" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Название дома"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Количество комнат"
          value={rooms}
          onChange={(e) => setRooms(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Количество жильцов"
          value={residents}
          onChange={(e) => setResidents(e.target.value)}
          required
        />
        <input
          type="number"
          step="0.01"
          placeholder="Дневной тариф"
          value={dayTariff}
          onChange={(e) => setDayTariff(e.target.value)}
          required
        />
        <input
          type="number"
          step="0.01"
          placeholder="Ночной тариф"
          value={nightTariff}
          onChange={(e) => setNightTariff(e.target.value)}
          required
        />
        <button type="submit">Добавить дом</button>
      </form>
    </div>
  );
}

export default AddHouseForm;