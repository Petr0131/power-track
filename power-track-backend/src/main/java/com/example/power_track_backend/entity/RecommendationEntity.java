package com.example.power_track_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recommendations")
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Double potentialSavings;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
    @ManyToOne
    @JoinColumn(name = "device_id") // Может быть null, например если рекомендация для дома в целом.
    private DeviceEntity deviceEntity;
}
