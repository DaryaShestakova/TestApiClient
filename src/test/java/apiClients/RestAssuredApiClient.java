package apiClients;

import entity.BaseRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.JsonParser;

import java.util.List;

import static apiClients.URLCreator.createURL;
import static io.restassured.RestAssured.given;

public class RestAssuredApiClient implements BaseApiClient{

    private static Logger logger = LogManager.getLogger();

    @Override
    public <T>BaseRequest post(String scheme, String hostname, String path, T jsonObject) {

        Response response = given()
                .baseUri(createURL(scheme, hostname, path, null))
                .header(Constants.HEADER, Constants.TYPE)
                .and()
                .body(JsonParser.parseToJson(jsonObject))
                .when()
                .post()
                .then()
                .extract().response();

        return createBaseRequest(response, jsonObject.getClass());
    }

    @Override
    public <T> BaseRequest get(String scheme, String hostname, String path, String query, Class<T> clazz) {
        Response response = given()
                .baseUri(createURL(scheme, hostname, path, query))
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .extract().response();

        return createBaseRequest(response, clazz);
    }

    @Override
    public <T> BaseRequest put(String scheme, String hostname, String path, T jsonObject) {

        Response response = given()
                .baseUri(createURL(scheme, hostname, path, null))
                .header(Constants.HEADER, Constants.TYPE)
                .and()
                .body(JsonParser.parseToJson(jsonObject))
                .when()
                .put()
                .then()
                .extract().response();

        return createBaseRequest(response, jsonObject.getClass());
    }

    @Override
    public <T> BaseRequest delete(String scheme, String hostname, String path, String query, Class<T> clazz) {

        Response response = given()
                .baseUri(createURL(scheme, hostname, path, query))
                .when()
                .delete()
                .then()
                .extract().response();

        return createBaseRequest(response, clazz);
    }


    public <T> BaseRequest createBaseRequest(Response response, Class<T> clazz){

        int statusCode = response.getStatusCode();
        List<Header> headers = response.getHeaders().asList();
        String jsonFromResponse = response.asString();
        logger.info(jsonFromResponse);
        T jsonObject = JsonParser.parseFromJson(null, jsonFromResponse, clazz);

        BaseRequest baseRoot = new BaseRequest(statusCode, headers, jsonObject);

        return baseRoot;
    }
}
