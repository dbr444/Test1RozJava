package test1roz.zad1.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = test1roz.zad1.shapes.Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = test1roz.zad1.shapes.Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = test1roz.zad1.shapes.Square.class, name = "square")
})
public interface Shape {
    double calculateArea();
    double calculatePerimeter();
    ShapeType getType();
}
