package test1roz.zad1.customserializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import test1roz.zad1.shapes.Circle;
import test1roz.zad1.shapes.Rectangle;
import test1roz.zad1.shapes.Square;
import test1roz.zad1.models.*;

import java.io.IOException;

public class ShapeSerializer extends StdSerializer<Shape> {

    public ShapeSerializer() {
        super(Shape.class);
    }

    @Override
    public void serialize(Shape shape, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", shape.getType().name().toLowerCase());

        switch (shape.getType()) {
            case SQUARE -> jsonGenerator.writeNumberField("side", ((Square) shape).getSide());
            case CIRCLE -> jsonGenerator.writeNumberField("radius", ((Circle) shape).getRadius());
            case RECTANGLE -> {
                jsonGenerator.writeNumberField("width", ((Rectangle) shape).getWidth());
                jsonGenerator.writeNumberField("height", ((Rectangle) shape).getHeight());
            }
        }

        jsonGenerator.writeEndObject();
    }
}
