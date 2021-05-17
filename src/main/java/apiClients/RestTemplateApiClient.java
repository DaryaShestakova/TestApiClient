package apiClients;

import constants.Method;
import entity.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import parser.JsonParser;

import java.net.URI;
import java.util.List;
import java.util.Map;


public class RestTemplateApiClient extends AbstractClient {

    private static Logger logger = LogManager.getLogger();
    RestTemplateEntity restTemplateEntity = new RestTemplateEntity();

    @Override
    public Object buildRequest(String url, Map<String, String> paths, Map<String, String> queries, Map<String,
            String> headers, Object jsonObject) {
        restTemplateEntity.setCreatedUrl(createURL(url, paths, queries));
        restTemplateEntity.setEntity(buildEntity(jsonObject, headers));
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    public HttpEntity buildEntity(Object jsonObject, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpHeaders.set(header.getKey(), header.getValue());
        }
        String stringJsonObject;
        if (jsonObject != null) {
            stringJsonObject = JsonParser.parseToJson(jsonObject);
        } else {
            stringJsonObject = null;
        }
        return new HttpEntity(stringJsonObject, httpHeaders);
    }

    public URI createURL(String url, Map<String, String> paths, Map<String, String> queries) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(paths)
                .toUri();
        if (queries != null) {
            for (Map.Entry<String, String> query : queries.entrySet()) {
                uri = UriComponentsBuilder
                        .fromUri(uri)
                        .queryParam(query.getKey(), query.getValue())
                        .build()
                        .toUri();
            }
        }
        return uri;
    }

    @Override
    public <T> BaseRequest doRequest(Object request, Method method, Class<T> clazz) {
        RestTemplate restTemplate = null;
        if (request instanceof RestTemplate) {
            restTemplate = (RestTemplate) request;
        }
        ResponseEntity restTemplateResponse = buildResponse(restTemplate, method);

        return deserializeObject(restTemplateResponse, clazz);
    }

    public <T> BaseRequest deserializeObject(ResponseEntity restTemplateResponse, Class<T> clazz) {
        int statusCode = restTemplateResponse.getStatusCode().value();
        List<String> headers = restTemplateResponse.getHeaders().getIfNoneMatch();
        String jsonFromResponse = restTemplateResponse.getBody().toString();
        logger.info(jsonFromResponse);
        T jsonObject = JsonParser.parseFromJson(null, jsonFromResponse, clazz);

        return new BaseRequest(statusCode, headers, jsonObject);
    }

    public ResponseEntity buildResponse(RestTemplate restTemplate, Method method) {
        ResponseEntity response;
        switch (method) {
            case POST:
                response = restTemplate.exchange(restTemplateEntity.getCreatedUrl(), HttpMethod.POST, restTemplateEntity.getEntity(), String.class);
                break;
            case GET:
                response = restTemplate.exchange(restTemplateEntity.getCreatedUrl(), HttpMethod.GET, restTemplateEntity.getEntity(), String.class);
                break;
            case PUT:
                response = restTemplate.exchange(restTemplateEntity.getCreatedUrl(), HttpMethod.PUT, restTemplateEntity.getEntity(), String.class);
                break;
            case DELETE:
                response = restTemplate.exchange(restTemplateEntity.getCreatedUrl(), HttpMethod.DELETE, restTemplateEntity.getEntity(), String.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        return response;
    }

    @Getter
    @Setter
    class RestTemplateEntity {
        private URI createdUrl;
        private HttpEntity<String> entity;
    }
}
