package lk.mcc.megacitycab.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lk.mcc.megacitycab.util.annotation.CustomJsonIgnoreModule;

public class GlobalObjectMapper {
    private static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .registerModule(new CustomJsonIgnoreModule());

    private GlobalObjectMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
