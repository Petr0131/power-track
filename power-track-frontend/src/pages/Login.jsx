import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../api/authApi';
import '../styles/global.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser({ username, password });
      const { userId, token, username: userUsername } = response.data.data;

      // Сохраняем токен, ID и имя пользователя в localStorage
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('username', userUsername);

      navigate('/dashboard'); // Перенаправляем на Dashboard
    } catch (error) {
      console.error('Login failed:', error);
      alert('Неверное имя пользователя или пароль');
    }
  };

  return (
    <div className="container">
      <h1 className="title">Вход</h1>
      <form className="form" onSubmit={handleLogin}>
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
        <button type="submit">Войти</button>
        <a href="/register" className="link">Нет аккаунта? Зарегистрируйтесь</a>
      </form>
    </div>
  );
}

export default Login;