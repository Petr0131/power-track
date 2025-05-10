package com.example.power_track_backend;

public enum EnergyEfficiencyCategory { //ToDo может это перечисление мне и не нужно
    A("A", 0.64),
    B("B", 0.80),
    C("C", 1.00),
    D("D", 1.25),
    E("E", 1.56),
    F("F", 1.95),
    G("G", 2.30);

    private final String label;
    private final Double coefficient;

    EnergyEfficiencyCategory(String label, Double coefficient) {
        this.label = label;
        this.coefficient = coefficient;
    }

    public String getLabel() {
        return label;
    }

    public Double getCoefficient() {
        return coefficient;
    }
}
