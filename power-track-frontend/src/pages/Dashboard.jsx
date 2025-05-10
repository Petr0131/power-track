import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getHouses } from '../api/houseApi';
import Card from '../components/Card';
import '../styles/global.css';

function Dashboard() {
  const [houses, setHouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchHouses = async () => {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          navigate('/login');
          return;
        }

        const response = await getHouses(userId);
        setHouses(response.data.data);
      } catch (error) {
        console.error('Ошибка при загрузке домов:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchHouses();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  const handleAddHouse = () => navigate('/add-house');
  const handleSettings = () => navigate('/settings');

  if (loading) {
    return <div className="container">Загрузка...</div>;
  }

  return (
    <div className="container">
      <div className="dashboard-box">
        {/* Заголовок */}
        <div className="title">Мои дома</div>

        {/* Карточки домов */}
        {houses.length > 0 ? (
          <div className="house-grid">
            {houses.map((house) => (
              <div key={house.id} onClick={() => navigate(`/house/${house.id}`)} className="card">
                <Card house={house} />
              </div>
            ))}
          </div>
        ) : (
          <p style={{ textAlign: 'center', fontSize: '1.2rem', marginTop: '20px' }}>
            У вас пока нет добавленных домов.
          </p>
        )}

        {/* Кнопки снизу */}
        <div className="button-group">
            <button className="dashboard-button" onClick={handleAddHouse}>Добавить дом</button>
            <button className="dashboard-button" onClick={handleSettings}>Настройки</button>
        <button className="dashboard-button" onClick={handleLogout}>Выйти</button>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
