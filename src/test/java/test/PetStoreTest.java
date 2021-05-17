package test;

import entity.BaseRequest;
import entity.DeleteRoot;
import entity.pet.Root;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import parser.JsonParser;

public class PetStoreTest extends BaseTest {

    @Test
    public void testPostRequest() {
        Root root = JsonParser.parseFromJson(FILE_TO_ADD_NEW_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.post(getBaseUrl() + getCommandPath(),
                getPathForPostAndPut(), null, getHeaders(), root, Root.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testGetRequest() {
        BaseRequest baseRequest = baseApiClient.get(getBaseUrl() + getCommandPath() + getIdPath(),
                getPathForGetAndDelete(), null, getHeaders(), Root.class);
        Root root = (Root) baseRequest.getJsonObject();
        Assert.assertEquals(200, baseRequest.getStatusCode());
        Assert.assertEquals(10, root.getId());
    }

    @Test
    public void testPutRequest() {
        Root root = JsonParser.parseFromJson(FILE_TO_UPDATE_PET, null, Root.class);
        BaseRequest baseRequest = baseApiClient.put(getBaseUrl() + getCommandPath(), getPathForPostAndPut(), null,
                getHeaders(), root, Root.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }

    @Test
    public void testDeleteRequest() {
        BaseRequest baseRequest = baseApiClient.delete(getBaseUrl() + getCommandPath() + getIdPath(),
                getPathForGetAndDelete(), null, getHeaders(), DeleteRoot.class);
        Assert.assertEquals(200, baseRequest.getStatusCode());
    }
}
