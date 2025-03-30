package com.example.power_track_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalConsumption;
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private HouseEntity houseEntity;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDeviceConsumptionEntity> deviceConsumptions = new ArrayList<>();

}
