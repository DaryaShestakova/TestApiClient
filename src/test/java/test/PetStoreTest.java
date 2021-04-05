package test;

import entity.BaseRequest;
import entity.DeleteRoot;
import entity.pet.Root;
import org.testng.Assert;
import org.testng.annotations.Test;
import parser.JsonParser;

public class PetStoreTest extends BaseTest{


    @Test
    public void testPostRequest(){
        Root root = JsonParser.parseFromJson(FILE_TO_ADD_NEW_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.post("https", "petstore.swagger.io/v2", "pet", root);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testGetRequest(){
        BaseRequest baseRequest = baseApiClient.get("https", "petstore.swagger.io/v2", "pet", "10", Root.class);
        Root root = (Root) baseRequest.getJsonObject();
        Assert.assertEquals(200, baseRequest.getStatusCode());
        Assert.assertEquals(10, root.getId());
    }

    @Test
    public void testPutRequest(){
        Root root = JsonParser.parseFromJson(FILE_TO_UPDATE_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.put("https", "petstore.swagger.io/v2", "pet", root);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testDeleteRequest(){
        BaseRequest baseRequest = baseApiClient.delete("https", "petstore.swagger.io/v2", "pet", "10", DeleteRoot.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }
}
