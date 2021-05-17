package test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "configuration")
@EnableConfigurationProperties
public class ServerProperties {

    private String baseUrl;
    private Map<String, String> paths;
    private Map<String, String> valuesForPostAndPut;
    private Map<String, String> valuesForGetAndDelete;
    private Map<String, String> headers;
}
