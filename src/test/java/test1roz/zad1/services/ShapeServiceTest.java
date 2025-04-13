package test1roz.zad1.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import test1roz.zad1.exceptions.InvalidListOrElementException;
import test1roz.zad1.exceptions.JsonOperationException;
import test1roz.zad1.shapes.ShapeFactory;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;
import test1roz.zad1.util.ObjectMapperHolder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShapeServiceTest {

    private ShapeService service;
    private ShapeFactory factory;

    @Before
    public void setUp() {
        factory = ShapeFactory.getInstance();
        service = new ShapeService(ObjectMapperHolder.INSTANCE.getObjectMapper());
    }

    @Test
    public void shouldReturnShapeWithLargestArea() {
        List<Shape> shapes = new ArrayList<>(List.of(
                factory.createCircle(2),
                factory.createSquare(10),
                factory.createRectangle(1, 1)
        ));

        Shape result = service.findShapeWithLargestArea(shapes);

        Assertions.assertThat(result)
                .isEqualTo(factory.createSquare(10));
    }

    @Test
    public void shouldReturnShapeWithLargestPerimeterOfType() {
        List<Shape> shapes = new ArrayList<>(List.of(
                factory.createCircle(2),
                factory.createCircle(10),
                factory.createSquare(5)
        ));

        Shape result = service.findShapeWithLargestPerimeterByType(shapes, ShapeType.CIRCLE);

        Assertions.assertThat(result)
                .isEqualTo(factory.createCircle(10));
    }

    @Test
    public void shouldExportAndImportShapes(){
        List<Shape> shapes = new ArrayList<>(List.of(
                factory.createSquare(3),
                factory.createRectangle(2, 6),
                factory.createCircle(4)
        ));

        String path = "target/test_shapes.json";
        service.exportToJson(shapes, path);

        File file = new File(path);
        Assertions.assertThat(file).exists();

        List<Shape> imported = service.importShapesFromJson(path);

        Assertions.assertThat(imported)
                .isNotNull()
                .hasSize(3)
                .containsExactlyElementsOf(shapes);
    }

    @Test
    public void shouldThrowWhenListIsNullInFindLargestArea() {
        Assertions.assertThatThrownBy(() -> service.findShapeWithLargestArea(null))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("The list is null, empty or an element is null.");
    }

    @Test
    public void shouldThrowWhenListContainsNullInFindLargestPerimeter() {
        List<Shape> shapes = new ArrayList<>(List.of(factory.createCircle(2)));
        shapes.add(null);

        Assertions.assertThatThrownBy(() -> service.findShapeWithLargestPerimeterByType(shapes, ShapeType.CIRCLE))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("The list is null, empty or an element is null.");
    }

    @Test
    public void shouldThrowWhenNoShapeOfGivenType() {
        List<Shape> shapes = new ArrayList<>(List.of(
                factory.createSquare(2),
                factory.createRectangle(3, 4)
        ));

        Assertions.assertThatThrownBy(() ->
                        service.findShapeWithLargestPerimeterByType(shapes, ShapeType.CIRCLE))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("No shape of type CIRCLE");
    }

    @Test
    public void shouldThrowWhenAreaIsZero() {
        Shape invalid = new Shape() {
            public double calculateArea() { return 0; }
            public double calculatePerimeter() { return 10; }
            public ShapeType getType() { return ShapeType.SQUARE; }
        };

        List<Shape> list = new ArrayList<>();
        list.add(invalid);

        Assertions.assertThatThrownBy(() -> service.findShapeWithLargestArea(list))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("Invalid area or perimeter");
    }

    @Test
    public void shouldThrowWhenPerimeterIsZero() {
        Shape invalid = new Shape() {
            public double calculateArea() { return 10; }
            public double calculatePerimeter() { return 0; }
            public ShapeType getType() { return ShapeType.SQUARE; }
        };

        List<Shape> list = new ArrayList<>();
        list.add(invalid);

        Assertions.assertThatThrownBy(() -> service.findShapeWithLargestArea(list))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("Invalid area or perimeter");
    }

    @Test
    public void shouldThrowWhenExportingNullList() {
        Assertions.assertThatThrownBy(() -> service.exportToJson(null, "somepath.json"))
                .isInstanceOf(InvalidListOrElementException.class)
                .hasMessageContaining("The list is null, empty or an element is null.");
    }

    @Test
    public void shouldThrowWhenImportFileDoesNotExist() {
        Assertions.assertThatThrownBy(() -> service.importShapesFromJson("nope/doesnotexist.json"))
                .isInstanceOf(JsonOperationException.class);
    }
}
