package apiClients;

import constants.Method;
import entity.BaseRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import parser.JsonParser;

import java.util.List;
import java.util.Map;


public class RestAssuredApiClient extends AbstractClient{

    private static Logger logger = LogManager.getLogger();

    @Override
    public Object buildRequest(String url, Map<String, String> paths, Map<String, String> queries, Map<String,
            String> headers, Object jsonObject) {
        RequestSpecification specification = RestAssured.given()
                .spec(createSpecification(url, paths, queries, headers, jsonObject))
                .and();
        return specification;
    }


    public RequestSpecification createSpecification (String url, Map<String, String> paths, Map<String,
            String> queries, Map<String, String> headers, Object jsonObject){
        RequestSpecBuilder spec = new RequestSpecBuilder();
        createUrl(spec, url, paths, queries);
        buildEntity(spec, headers, jsonObject);
        return spec.build();
    }

    public void createUrl (RequestSpecBuilder spec, String url, Map<String, String> paths, Map<String, String> queries){
        String baseURL = StringUtils.substringBefore(url, "/{");
        String endpoint = StringUtils.substringAfter(url,"io/");
            spec.setBaseUri(baseURL)
                .setBasePath(endpoint);
        if (paths != null) {
            spec.addPathParams(paths);
        }
        if (queries != null){
            spec.addQueryParams(queries);
        }
    }

    public void buildEntity (RequestSpecBuilder spec, Map<String, String> headers, Object jsonObject){
        if (headers != null){
            spec.addHeaders(headers)
                    .and();
        }
        if(jsonObject != null){
            spec.setBody(JsonParser.parseToJson(jsonObject));
        }
    }

    @Override
    public <T> BaseRequest doRequest(Object request, Method method, Class<T> clazz) {
        RequestSpecification specification = (RequestSpecification) request;
        Response restAssuredResponse = buildResponse(specification, method);

        return deserializeObject(restAssuredResponse, clazz);
    }

    public <T> BaseRequest deserializeObject(Response restAssuredResponse, Class<T> clazz){
        int statusCode = restAssuredResponse.getStatusCode();
        List<Header> headers = restAssuredResponse.getHeaders().asList();
        String jsonFromResponse = restAssuredResponse.asString();
        logger.info(jsonFromResponse);
        T jsonObject = JsonParser.parseFromJson(null, jsonFromResponse, clazz);

        return new BaseRequest(statusCode, headers, jsonObject);
    }


    public Response buildResponse(RequestSpecification specification, Method method){
        Response response;
        switch (method){
            case POST:
                response = specification.post();
                break;
            case GET:
                response = specification.get();
                break;
            case PUT:
                response = specification.put();
                break;
            case DELETE:
                response = specification.delete();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        return response;
    }

}
