package test;

import apiClients.BaseApiClient;
import apiClients.HttpApiClient;
import apiClients.RestAssuredApiClient;
import org.testng.annotations.BeforeTest;

import java.io.File;

public class BaseTest {

    public static final File FILE_TO_ADD_NEW_PET = new File("src/test/resources/json_new_pet.json");
    public static final File FILE_TO_UPDATE_PET = new File("src/test/resources/json_update_pet.json");
    public BaseApiClient baseApiClient;


    @BeforeTest
    public void setup() {
        baseApiClient = new RestAssuredApiClient();
    }
}
