package test1roz.zad1.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import test1roz.zad1.customserializers.ShapeDeserializer;
import test1roz.zad1.customserializers.ShapeSerializer;

import test1roz.zad1.models.Shape;

public enum ObjectMapperHolder {

    INSTANCE;

    private final ObjectMapper objectMapper;

    ObjectMapperHolder() {
        objectMapper = createAndConfigure();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static ObjectMapper createAndConfigure() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        SimpleModule shapeModule = new SimpleModule("ShapeModule");
        shapeModule.addSerializer(Shape.class, new ShapeSerializer());
        shapeModule.addDeserializer(Shape.class, new ShapeDeserializer());
        mapper.registerModule(shapeModule);

        return mapper;
    }
}