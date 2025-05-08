package com.example.power_track_backend;

public enum RecommendationPriority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int level;

    RecommendationPriority(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
