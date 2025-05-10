package com.example.power_track_backend;

public enum DeviceProfile {
    REFRIGERATOR(200, 1440),
    HAIR_DRYER(1500, 5),
    TELEVISION(100, 240),
    AIR_CONDITIONER(1200, 480);

    private final int power;
    private final int defaultDailyUsageMinutes;

    DeviceProfile(int power, int defaultDailyUsageMinutes) {
        this.power = power;
        this.defaultDailyUsageMinutes = defaultDailyUsageMinutes;
    }

    public int getPower() {
        return power;
    }

    public int getDefaultDailyUsageMinutes() {
        return defaultDailyUsageMinutes;
    }
}
