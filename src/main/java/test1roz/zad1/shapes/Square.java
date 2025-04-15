package test1roz.zad1.shapes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.util.Objects;
@JsonTypeName("square")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public final class Square implements Shape {

    private final double side;

    private Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    static Square createSquare(double side) {
        return new Square(side);
    }

    @JsonCreator
    static Square createForDeserialization(@JsonProperty("side") double side) {
        return ShapeFactory.getInstance().createSquare(side);
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * side;
    }

    @Override
    public ShapeType getType() {
        return ShapeType.SQUARE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Double.compare(square.side, side) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side);
    }

    @Override
    public String toString() {
        return "Square{" +
                "side=" + side +
                '}';
    }
}
