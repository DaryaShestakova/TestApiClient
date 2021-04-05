package apiClients;

import entity.BaseRequest;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static apiClients.URLCreator.createURL;
import static parser.JsonParser.parseFromJson;
import static parser.JsonParser.parseToJson;

public class HttpApiClient implements BaseApiClient{

    private static Logger logger = LogManager.getLogger();

    @SneakyThrows
    @Override
    public <T>BaseRequest post(String scheme, String hostname, String path, T jsonObject) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(createURL(scheme, hostname, path, null));
        post.setHeader(Constants.HEADER, Constants.TYPE);
        post.setEntity(new StringEntity(parseToJson(jsonObject)));
        HttpResponse response = httpClient.execute(post);

        return createBaseRequest(response, jsonObject.getClass());
    }

    @SneakyThrows
    @Override
    public <T> BaseRequest get(String scheme, String hostname, String path, String query, Class<T> clazz) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(createURL(scheme, hostname, path, query));
        get.setHeader(Constants.HEADER, Constants.TYPE);
        HttpResponse response = httpClient.execute(get);

        return createBaseRequest(response, clazz);
    }

    @SneakyThrows
    @Override
    public <T>BaseRequest put(String scheme, String hostname, String path, T jsonObject) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(createURL(scheme, hostname, path, null));
        put.setHeader(Constants.HEADER, Constants.TYPE);
        put.setEntity(new StringEntity(parseToJson(jsonObject)));
        HttpResponse response = httpClient.execute(put);

        return createBaseRequest(response, jsonObject.getClass());
    }

    @SneakyThrows
    @Override
    public <T> BaseRequest delete(String scheme, String hostname, String path, String query, Class<T> clazz) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(createURL(scheme, hostname, path, query));
        HttpResponse response = httpClient.execute(delete);

        return createBaseRequest(response, clazz);
    }

    @SneakyThrows
    public <T> BaseRequest createBaseRequest(HttpResponse response, Class<T> clazz){

        int statusCode = response.getStatusLine().getStatusCode();
        List<Header> headers = Arrays.asList(response.getAllHeaders());
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        logger.info(jsonFromResponse);
        T jsonObject = parseFromJson(null, jsonFromResponse, clazz);

        BaseRequest baseRoot = new BaseRequest(statusCode, headers, jsonObject);

        return baseRoot;
    }
}
