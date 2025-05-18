import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../api/authApi';
import '../styles/global.css';

function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  // Сбрасываем состояние при монтировании
  useEffect(() => {
    setUsername('');
    setPassword('');
  }, []);

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await registerUser({ username, password });
      alert('Регистрация успешна! Пожалуйста, войдите.');
      navigate('/login'); // Перенаправляем на страницу входа
    } catch (error) {
      console.error('Registration failed:', error);
      alert('Ошибка регистрации. Попробуйте снова.');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Регистрация</h1>
      <form className="form" onSubmit={handleRegister}>
        <input
          type="text"
          placeholder="Имя пользователя"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Пароль"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Зарегистрироваться</button>
        <a href="/login" className="link">Уже есть аккаунт? Войдите</a>
      </form>
    </div>
  );
}

export default Register;