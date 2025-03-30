package com.example.power_track_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendations")
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity deviceEntity;
}
