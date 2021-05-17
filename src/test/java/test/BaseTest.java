package test;

import apiClients.BaseApiClient;
import apiClients.RestAssuredApiClient;
import constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Map;

@SpringBootTest(classes = ApiProperties.class)
public class BaseTest {

    public static final File FILE_TO_ADD_NEW_PET = new File(Constants.JSON_NEW_PET);
    public static final File FILE_TO_UPDATE_PET = new File(Constants.JSON_UPDATE_PET);
    public BaseApiClient baseApiClient;

    public Map<String, String> getPathForPostAndPut() {
        return ApiProperties.getValuesForPostAndPut();
    }

    public Map<String, String> getPathForGetAndDelete() {
        return ApiProperties.getValuesForGetAndDelete();
    }

    public Map<String, String> getHeaders() {
        return ApiProperties.getHeaders();
    }

    public String getBaseUrl() {
        return ApiProperties.getBaseUrl();
    }

    public String getCommandPath() {
        return ApiProperties.getPaths().get("command");
    }

    public String getIdPath() {
        return ApiProperties.getPaths().get("id");
    }

    @Autowired
    private ApiProperties ApiProperties;

    @BeforeEach
    public void before() {
        baseApiClient = new RestAssuredApiClient();
    }
}
