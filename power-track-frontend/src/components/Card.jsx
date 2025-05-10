import React from 'react';
import '../styles/global.css';

function Card({ house }) {
  return (
    <div
      style={{
        width: '200px',
        padding: '15px',
        background: 'rgba(0, 0, 0, 0.7)',
        borderRadius: '10px',
        boxShadow: '0 4px 10px rgba(0, 0, 0, 0.3)',
        textAlign: 'center',
      }}
    >
      <h3>{house.name}</h3>
      <p>Количество комнат: {house.rooms}</p>
      <p>Жильцов: {house.residents}</p>
    </div>
  );
}

export default Card;