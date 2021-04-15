package apiClients;

import constants.Method;
import entity.BaseRequest;

import java.util.Map;

public abstract class AbstractClient implements BaseApiClient{
    @Override
    public <T> BaseRequest post(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, T jsonObject, Class<T> clazz){
        Object request = buildRequest(url, paths, queries, headers, jsonObject);
        return doRequest(request, Method.POST, clazz);
    }

    @Override
    public <T> BaseRequest get(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz){
        Object response = buildRequest(url, paths, queries, headers, null);
        return doRequest(response, Method.GET, clazz);
    }

    @Override
    public <T> BaseRequest put(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, T jsonObject, Class<T> clazz){
        Object response = buildRequest(url, paths, queries, headers, jsonObject);
        return doRequest(response, Method.PUT, clazz);
    }

    @Override
    public <T> BaseRequest delete(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz){
        Object response = buildRequest(url, paths, queries, headers, null);
        return doRequest(response, Method.DELETE, clazz);
    }

    public abstract Object buildRequest(String url, Map<String, String> paths, Map<String, String> queries, Map<String,
            String> headers, Object jsonObject);

    public abstract <T> BaseRequest doRequest(Object response, Method method, Class<T> clazz);
}
