package apiClients;

import constants.Method;
import entity.BaseRequest;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static apiClients.HttpApiClient.JsonObject.getJsonObject;
import static parser.JsonParser.parseFromJson;
import static parser.JsonParser.parseToJson;

public class HttpApiClient extends AbstractClient{

    private static Logger logger = LogManager.getLogger();

    @SneakyThrows
    @Override
    public Object buildRequest(String url, Map<String, String> paths, Map<String, String> queries,
                               Map<String, String> headers, Object jsonObject) {
        URI uri = URI.create(createURL(url, paths, queries));
        HttpRequest.Builder request = HttpRequest.newBuilder().uri(uri);
        buildHeaders(request, headers);
        buildBody(jsonObject);
        return request;
    }

    @SneakyThrows
    public String createURL(String url, Map<String, String> paths, Map<String, String> queries) {
        if (paths != null) {
            for (Map.Entry<String, String> path : paths.entrySet()) {
                url = url.replaceAll("\\{" + path.getKey() + "\\}", path.getValue());
            }
        }
        URIBuilder builder = new URIBuilder(url);
        if (queries != null){
            for (Map.Entry<String, String> query : queries.entrySet()) {
                builder.setParameter(query.getKey(), query.getValue());
            }
        }
        String createdUrl = builder.build().toString();
        logger.info(createdUrl);
        return createdUrl;
    }


    public void buildHeaders(HttpRequest.Builder request, Map<String, String> headers){
        for (Map.Entry<String, String> header : headers.entrySet()){
            request.header(header.getKey(), header.getValue());
        }
    }

    public void buildBody(Object jsonObject){
        if(jsonObject != null){
            JsonObject.setJsonObject(HttpRequest.BodyPublishers.ofString(parseToJson(jsonObject)));
        }else {
            JsonObject.setJsonObject(null);
        }
    }

    @Override
    public <T> BaseRequest doRequest(Object request, Method method, Class<T> clazz) {
        HttpRequest.Builder builderRequest = (HttpRequest.Builder) request;
        HttpResponse httpResponse = buildResponse(builderRequest, method);

        return deserializeObject(httpResponse, clazz);
    }

    public <T> BaseRequest deserializeObject(HttpResponse httpResponse, Class<T> clazz){
        int statusCode = httpResponse.statusCode();
        List<HttpHeaders> headers = Arrays.asList(httpResponse.headers());
        String jsonFromResponse = httpResponse.body().toString();
        logger.info(jsonFromResponse);
        T jsonObject = parseFromJson(null, jsonFromResponse, clazz);

        return new BaseRequest(statusCode, headers, jsonObject);
    }

    @SneakyThrows
    public HttpResponse buildResponse(HttpRequest.Builder request, Method method){
        switch (method){
            case POST:
                request.POST(getJsonObject());
                break;
            case GET:
                request.GET();
                break;
            case PUT:
                request.PUT(getJsonObject());
                break;
            case DELETE:
                request.DELETE();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse httpResponse = client.send(request.build(), HttpResponse.BodyHandlers.ofString());
        return httpResponse;
    }

    static class JsonObject {
        static HttpRequest.BodyPublisher jsonObject;

        public static HttpRequest.BodyPublisher getJsonObject() {
            return jsonObject;
        }

        public static void setJsonObject(HttpRequest.BodyPublisher jsonObject) {
            JsonObject.jsonObject = jsonObject;
        }
    }
}

