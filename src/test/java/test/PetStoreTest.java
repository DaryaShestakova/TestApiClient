package test;

import entity.BaseRequest;
import entity.DeleteRoot;
import entity.pet.Root;
import org.testng.Assert;
import org.testng.annotations.Test;
import parser.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class PetStoreTest extends BaseTest {

    private static final Map<String, String> PATH_FOR_POST_AND_PUT = new HashMap<>();
    private static final Map<String, String> PATH_FOR_GET_AND_DELETE = new HashMap<>();
    private static final Map<String, String> query  = new HashMap<>();
    private static final Map<String, String> headers  = new HashMap<>();
    public static final String BASE_URL = "https://petstore.swagger.io/{type}";
    public static final String COMMAND = "/{animal}";
    public static final String ID = "/{id}";

    static {
        PATH_FOR_POST_AND_PUT.put("type", "v2");
        PATH_FOR_POST_AND_PUT.put("animal", "pet");
        PATH_FOR_GET_AND_DELETE.put("type", "v2");
        PATH_FOR_GET_AND_DELETE.put("animal", "pet");
        PATH_FOR_GET_AND_DELETE.put("id", "10");
        query.put("id", "10");
        headers.put("Content-type", "application/json");
    }

    @Test
    public void testPostRequest(){
        Root root = JsonParser.parseFromJson(FILE_TO_ADD_NEW_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.post(BASE_URL + COMMAND, PATH_FOR_POST_AND_PUT, null, headers, root, Root.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testGetRequest(){
        BaseRequest baseRequest = baseApiClient.get(BASE_URL + COMMAND + ID, PATH_FOR_GET_AND_DELETE, null, headers, Root.class);
        Root root = (Root) baseRequest.getJsonObject();
        softAssert.assertEquals(200, baseRequest.getStatusCode());
        softAssert.assertEquals(10, root.getId());
        softAssert.assertAll();
    }

    @Test
    public void testPutRequest(){
        Root root = JsonParser.parseFromJson(FILE_TO_UPDATE_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.put(BASE_URL + COMMAND, PATH_FOR_POST_AND_PUT, null, headers, root, Root.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testDeleteRequest(){
        BaseRequest baseRequest = baseApiClient.delete(BASE_URL + COMMAND + ID, PATH_FOR_GET_AND_DELETE, null, headers, DeleteRoot.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }
}
