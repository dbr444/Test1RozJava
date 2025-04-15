package test1roz.zad1.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperHolder {
    INSTANCE;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}