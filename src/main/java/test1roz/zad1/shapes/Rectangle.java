package test1roz.zad1.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.util.Objects;
@JsonTypeName("rectangle")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public final class Rectangle implements Shape {

    private final double width;
    private final double height;
    private final ShapeType type = ShapeType.RECTANGLE;

    private Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    static Rectangle createRectangle(double width, double height) {
        return new Rectangle(width, height);
    }

    @JsonCreator
    static Rectangle createForDeserialization(
            @JsonProperty("width") double width,
            @JsonProperty("height") double height) {
        return ShapeFactory.getInstance().createRectangle(width, height);
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public ShapeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 && Double.compare(rectangle.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
