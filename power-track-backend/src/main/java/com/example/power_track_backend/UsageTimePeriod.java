package com.example.power_track_backend;

public enum UsageTimePeriod {
    DAY_ONLY(1, 0),       // Только днем
    NIGHT_ONLY(0, 1),     // Только ночью
    BOTH_DAY_NIGHT(0.6, 0.4); // День и ночь

    private final double dayFactor;
    private final double nightFactor;

    UsageTimePeriod(double dayFactor, double nightFactor) {
        this.dayFactor = dayFactor;
        this.nightFactor = nightFactor;
    }

    public double getDayFactor() {
        return dayFactor;
    }

    public double getNightFactor() {
        return nightFactor;
    }
}
