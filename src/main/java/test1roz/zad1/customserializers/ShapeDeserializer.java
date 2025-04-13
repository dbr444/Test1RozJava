package test1roz.zad1.customserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import test1roz.zad1.shapes.ShapeFactory;
import test1roz.zad1.models.Shape;
import test1roz.zad1.models.ShapeType;

import java.io.IOException;

public class ShapeDeserializer extends StdDeserializer<Shape> {

    public ShapeDeserializer() {
        super(Shape.class);
    }

    @Override
    public Shape deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ShapeFactory factory = ShapeFactory.getInstance();

        String type = node.get("type").asText().toUpperCase();
        ShapeType shapeType = ShapeType.valueOf(type);

        return switch (shapeType) {
            case SQUARE -> factory.createSquare(node.get("side").asDouble());
            case CIRCLE -> factory.createCircle(node.get("radius").asDouble());
            case RECTANGLE -> factory.createRectangle(
                    node.get("width").asDouble(), node.get("height").asDouble());
        };
    }
}
