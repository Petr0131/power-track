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
    private String name; // Todo сделать требование чтобы это поле было уникальным.
    private Integer rooms;
    private Integer residents; // ToDo решить использовать ли Integer или int
    private Double dayTariff; // ToDo решить использовать ли Double или double
    private Double nightTariff; // ToDo решить использовать ли Double или double

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeviceEntity> deviceEntities = new ArrayList<>();
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reportEntities = new ArrayList<>();
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendationEntity> recommendationEntities = new ArrayList<>();
    @OneToMany(mappedBy = "houseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdviceEntity> adviceEntities = new ArrayList<>();
}
