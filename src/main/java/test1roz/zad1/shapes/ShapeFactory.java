package test1roz.zad1.shapes;

import test1roz.zad1.models.*;

import java.util.concurrent.ConcurrentHashMap;

public class ShapeFactory {

    private static final ShapeFactory INSTANCE = new ShapeFactory();

    private final ConcurrentHashMap<String, Shape> cache = new ConcurrentHashMap<>();

    public ShapeFactory() {}

    public static ShapeFactory getInstance() {
        return INSTANCE;
    }

    public Square createSquare(double side) {
        return (Square) cache.computeIfAbsent("square:" + side, k -> Square.createSquare(side));
    }

    public Circle createCircle(double radius) {
        return (Circle) cache.computeIfAbsent("circle:" + radius, k -> Circle.createCircle(radius));
    }

    public Rectangle createRectangle(double width, double height) {
        return (Rectangle) cache.computeIfAbsent("rectangle:" + width + ":" + height, k -> Rectangle.createRectangle(width, height));
    }
}
