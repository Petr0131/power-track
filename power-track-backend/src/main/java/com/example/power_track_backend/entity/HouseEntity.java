package com.example.power_track_backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "houses")
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer rooms;
    private Integer residents; // ToDo решить использовать ли Integer или int
    private Double dayTariff; // ToDo решить использовать ли Double или double
    private Double nightTariff;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceEntity> deviceEntities = new ArrayList<>();
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportEntities = new ArrayList<>();
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationEntity> recommendationEntities = new ArrayList<>();
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdviceEntity> adviceEntities = new ArrayList<>();
}
