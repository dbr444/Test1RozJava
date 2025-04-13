package test1roz.zad1.app;

import test1roz.zad1.shapes.ShapeFactory;
import test1roz.zad1.shapes.Circle;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;
import test1roz.zad1.services.ShapeService;
import test1roz.zad1.util.ObjectMapperHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShapeRunner {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        ShapeService shapeService = new ShapeService(ObjectMapperHolder.INSTANCE.getObjectMapper());

        List<Shape> shapes = new ArrayList<>(List.of(
                shapeFactory.createSquare(5),
                shapeFactory.createCircle(10),
                shapeFactory.createRectangle(10,15)
        ));

        String path = "shapes.json";
        shapeService.exportToJson(shapes, path);
        List<Shape> importedShapesFromJson = shapeService.importShapesFromJson(path);

        Shape shapeWithLargestArea = shapeService.findShapeWithLargestArea(importedShapesFromJson);
        System.out.println("Largest area: " + shapeWithLargestArea);

        Shape circleWithLargestPerimeter = shapeService.findShapeWithLargestPerimeterByType(importedShapesFromJson, ShapeType.SQUARE);
        System.out.println("Largest perimeter by type: " + circleWithLargestPerimeter);

        Circle c1 = shapeFactory.createCircle(22);
        Circle c2 = shapeFactory.createCircle(22);
        System.out.println(c1 == c2);

        if (!importedShapesFromJson.isEmpty()) {
            System.out.println("zaliczylem test!:)");
        }
    }
}
