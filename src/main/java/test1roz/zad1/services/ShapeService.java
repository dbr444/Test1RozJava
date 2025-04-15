package test1roz.zad1.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import test1roz.zad1.exceptions.InvalidListOrElementException;
import test1roz.zad1.exceptions.JsonOperationException;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ShapeService {

    private final ObjectMapper mapper;

    public ShapeService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Shape findShapeWithLargestArea(List<Shape> shapes) {
        validateShapeList(shapes);
        return shapes.stream()
                .peek(this::validateShape)
                .max(Comparator.comparingDouble(Shape::calculateArea))
                .get();
    }

    public Shape findShapeWithLargestPerimeterByType(List<Shape> shapes, ShapeType type) {
        validateShapeList(shapes);
        return shapes.stream()
                .peek(this::validateShape)
                .filter(shape -> shape.getType() == type)
                .max(Comparator.comparingDouble(Shape::calculatePerimeter))
                .orElseThrow(() -> new InvalidListOrElementException("No shape of type " + type + " found."));
    }


    public void exportToJson(List<Shape> shapes, String path) {
        validateShapeList(shapes);
        shapes.forEach(this::validateShape);
        try {
            mapper.writeValue(new File(path), shapes);
        } catch (IOException e) {
            throw new JsonOperationException("Error while writing to JSON file", e);
        }
    }

    public List<Shape> importShapesFromJson(String path) {
        try {
            List<Shape> shapes = mapper.readValue(new File(path), new TypeReference<>() {});
            validateShapeList(shapes);
            shapes.forEach(this::validateShape);
            return shapes;
        } catch (IOException e) {
            throw new JsonOperationException("Error while reading from JSON file", e);
        }
    }


    private void validateShapeList(List<Shape> shapes) {
        Optional.ofNullable(shapes)
                .filter(list -> !list.isEmpty() && list.stream().noneMatch(Objects::isNull))
                .orElseThrow(() -> new InvalidListOrElementException("The list is null, empty or an element is null."));
    }

    private void validateShape(Shape shape) {
        if (shape.calculateArea() <= 0 || shape.calculatePerimeter() <= 0) {
            throw new InvalidListOrElementException("Invalid area or perimeter");
        }
    }
}
