package test;

import apiClients.BaseApiClient;
import apiClients.RestAssuredApiClient;
import constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Map;

@SpringBootTest(classes = ServerProperties.class)
public class BaseTest {

    public static final File FILE_TO_ADD_NEW_PET = new File(Constants.JSON_NEW_PET);
    public static final File FILE_TO_UPDATE_PET = new File(Constants.JSON_UPDATE_PET);
    public BaseApiClient baseApiClient;

    public Map<String, String> getPathForPostAndPut() {
        return serverProperties.getValuesForPostAndPut();
    }

    public Map<String, String> getPathForGetAndDelete() {
        return serverProperties.getValuesForGetAndDelete();
    }

    public Map<String, String> getHeaders() {
        return serverProperties.getHeaders();
    }

    public String getBaseUrl() {
        return serverProperties.getBaseUrl();
    }

    public String getCommandPath() {
        return serverProperties.getPaths().get("command");
    }

    public String getIdPath() {
        return serverProperties.getPaths().get("id");
    }

    @Autowired
    private ServerProperties serverProperties;

    @BeforeEach
    public void before() {
        baseApiClient = new RestAssuredApiClient();
    }
}
