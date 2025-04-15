package test1roz.zad1.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.util.Objects;
@JsonTypeName("circle")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public final class Circle implements Shape {

    private final double radius;
    private final ShapeType type = ShapeType.CIRCLE;

    private Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    static Circle createCircle(double radius) {
        return new Circle(radius);
    }

    @JsonCreator
    public static Circle createForDeserialization(@JsonProperty("radius") double radius) {
        return ShapeFactory.getInstance().createCircle(radius);
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public ShapeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}
