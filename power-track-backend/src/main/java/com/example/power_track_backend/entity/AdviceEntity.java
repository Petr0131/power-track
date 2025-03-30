package com.example.power_track_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "advice")
public class AdviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Double potentialSavings;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;
}
