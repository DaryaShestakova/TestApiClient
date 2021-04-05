package apiClients;

import entity.BaseRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import parser.JsonParser;

import java.util.List;

import static apiClients.URLCreator.createURL;

public class RestTemplateApiClient implements BaseApiClient{

    public RestTemplate restTemplate = new RestTemplate();
    public HttpHeaders headers = new HttpHeaders();
    private static Logger logger = LogManager.getLogger();

    @Override
    public <T>BaseRequest post(String scheme, String hostname, String path, T jsonObject) {

        headers.add(Constants.HEADER, Constants.TYPE);
        HttpEntity<String> entity = new HttpEntity<String>(JsonParser.parseToJson(jsonObject), headers);
        String url = createURL(scheme, hostname, path, null);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return createBaseRequest(response, jsonObject.getClass());
    }

    @Override
    public <T> BaseRequest get(String scheme, String hostname, String path, String query, Class<T> clazz) {

        String url = createURL(scheme, hostname, path, query);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        return createBaseRequest(response, clazz);
    }

    @Override
    public <T>BaseRequest put(String scheme, String hostname, String path, T jsonObject) {

        headers.add(Constants.HEADER, Constants.TYPE);
        HttpEntity<String> entity = new HttpEntity<String>(JsonParser.parseToJson(jsonObject), headers);
        String url = createURL(scheme, hostname, path, null);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return createBaseRequest(response, jsonObject.getClass());
    }

    @Override
    public <T>BaseRequest delete(String scheme, String hostname, String path, String query, Class<T> clazz) {

        String url = createURL(scheme, hostname, path, query);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        return createBaseRequest(response, clazz);
    }

    public <T> BaseRequest createBaseRequest(ResponseEntity response, Class<T> clazz){

        int statusCode = response.getStatusCode().value();
        List<String> headers = response.getHeaders().getIfNoneMatch();
        String jsonFromResponse = response.getBody().toString();
        logger.info(jsonFromResponse);
        T jsonObject = JsonParser.parseFromJson(null, jsonFromResponse, clazz);

        BaseRequest baseRoot = new BaseRequest(statusCode, headers, jsonObject);

        return baseRoot;
    }


}
