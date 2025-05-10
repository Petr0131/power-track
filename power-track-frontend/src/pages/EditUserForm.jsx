import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getUserByUsername, patchUser } from '../api/UserApi';
import '../styles/global.css';

function EditUserForm() {
  const navigate = useNavigate();

  // Состояния для полей формы
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [currencyType, setCurrencyType] = useState('');

  // Перечисления для выпадающих списков
  const currencyTypes = ['USD', 'RUB', 'EUR'];

  // Загрузка данных пользователя при монтировании компонента
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const username = localStorage.getItem('username');
        if (!username) {
          navigate('/login');
          return;
        }

        const response = await getUserByUsername(username);
        const user = response.data.data;

        // Заполняем поля формы данными пользователя
        setUsername(user.username);
        setCurrencyType(user.currencyType);
      } catch (error) {
        console.error('Ошибка при загрузке данных пользователя:', error);
        alert('Не удалось загрузить данные пользователя.');
      }
    };

    fetchUserData();
  }, [navigate]);

  // Обработчик отправки формы
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Создаём объект с изменёнными данными
      const updatedUserData = {};
      if (username !== localStorage.getItem('username')) {
        updatedUserData.username = username;
      }
      if (password) {
        updatedUserData.password = password;
      }
      if (currencyType) {
        updatedUserData.currencyType = currencyType;
      }

      // Если нет изменений, выходим
      if (Object.keys(updatedUserData).length === 0) {
        alert('Нет изменений для сохранения.');
        return;
      }

      // Отправляем PATCH-запрос
      const usernameFromStorage = localStorage.getItem('username');
      await patchUser(usernameFromStorage, updatedUserData);

      alert('Данные пользователя успешно обновлены!');
      navigate('/dashboard'); // Перенаправляем на страницу дашборда
    } catch (error) {
      console.error('Ошибка при обновлении данных пользователя:', error);
      alert('Не удалось обновить данные пользователя.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Редактировать профиль</h1>
      <form className="form" onSubmit={handleSubmit}>
        {/* Имя пользователя */}
        <input
          type="text"
          placeholder="Имя пользователя"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        {/* Пароль */}
        <input
          type="password"
          placeholder="Новый пароль (оставьте пустым, если не хотите менять)"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        {/* Валюта */}
        <select
          value={currencyType}
          onChange={(e) => setCurrencyType(e.target.value)}
          required
        >
          <option value="">Выберите валюту</option>
          {currencyTypes.map((currency) => (
            <option key={currency} value={currency}>
              {currency}
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

export default EditUserForm;