package com.example.power_track_backend;

public enum CurrencyType {
    USD("$"),
    RUB("₽"),
    EUR("€");

    private final String symbol;

    CurrencyType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
