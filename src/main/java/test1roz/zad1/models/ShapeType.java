package test1roz.zad1.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ShapeType {
    CIRCLE("circle"),
    SQUARE("square"),
    RECTANGLE("rectangle");

    private final String value;

    ShapeType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
