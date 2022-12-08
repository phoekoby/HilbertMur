package org.example;

import java.util.HashMap;
import java.util.Map;

public enum Degree {
    ZERO(0, "⁰"),
    ONE(1,"¹"),
    TWO(2, "²"),
    THREE(3, "³"),
    FOUR(4, "⁴"),
    FIVE(5,"⁵"),
    SIX(6, "⁶"),
    SEVEN(7, "⁷"),
    EIGHT(8, "⁸"),

    NINE(9,"⁹"),

    MINUS(11,"⁻"),;
    private final  int value;
    private final String symbol;
    Degree(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }
    private static final Map<Integer, Degree> lookup = new HashMap<>();

    static {
        for (Degree d : Degree.values()) {
            lookup.put(d.getValue(), d);
        }
    }
    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Degree get(Integer value) {
        return lookup.get(value);
    }
}
