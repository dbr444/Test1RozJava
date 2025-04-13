package test1roz.zad1.shapes;

import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.util.Objects;

public final class Square implements Shape {

    private final double side;
    private final ShapeType type = ShapeType.SQUARE;

    private Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    static Square createSquare(double side) {
        return new Square(side);
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
        return type;
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
