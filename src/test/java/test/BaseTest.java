package test;

import apiClients.BaseApiClient;
import apiClients.HttpApiClient;
import apiClients.RestAssuredApiClient;
import apiClients.RestTemplateApiClient;
import constants.Constants;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.io.File;

public class BaseTest {

    public static final File FILE_TO_ADD_NEW_PET = new File(Constants.JSON_NEW_PET);
    public static final File FILE_TO_UPDATE_PET = new File(Constants.JSON_UPDATE_PET);
    public BaseApiClient baseApiClient;
    protected SoftAssert softAssert = new SoftAssert();


    @BeforeTest
    public void setup() {
        baseApiClient = new RestAssuredApiClient();
    }
}
