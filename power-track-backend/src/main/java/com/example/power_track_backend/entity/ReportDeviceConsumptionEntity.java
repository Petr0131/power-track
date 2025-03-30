package com.example.power_track_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_device_consumptions")
public class ReportDeviceConsumptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalConsumption;
    private Double dayConsumption;
    private Double nightConsumption;
    private Double estimatedCost;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private  ReportEntity reportEntity;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceEntity deviceEntity;
}
