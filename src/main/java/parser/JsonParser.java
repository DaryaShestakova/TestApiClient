package parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public class JsonParser {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <T> T parseFromJson(File file, String jsonFromResponse, Class<T> clazz) {
        return (file == null) ? objectMapper.readValue(jsonFromResponse, clazz) : objectMapper.readValue(file, clazz);
    }

    @SneakyThrows
    public static <T> String parseToJson(T clazz) {
        String rootAsJson = objectMapper.writeValueAsString(clazz);
        return rootAsJson;
    }
}
