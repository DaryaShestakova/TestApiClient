package apiClients;

import constants.Method;
import entity.BaseRequest;

import java.util.Map;

public abstract class AbstractClient implements BaseApiClient{
    @Override
    public <T, K> BaseRequest post(String url, Map<String, String> paths, Map<String, String> queries, Map<String,
            String> headers, T jsonObject, Class<K> clazz){
        Object request = buildRequest(url, paths, queries, headers, jsonObject);
        return doRequest(request, Method.POST, clazz);
    }

    @Override
    public <T> BaseRequest get(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz){
        Object request = buildRequest(url, paths, queries, headers, null);
        return doRequest(request, Method.GET, clazz);
    }

    @Override
    public <T, K> BaseRequest put(String url, Map<String, String> paths, Map<String, String> queries,
                                  Map<String, String> headers, T jsonObject, Class<K> clazz){
        Object request = buildRequest(url, paths, queries, headers, jsonObject);
        return doRequest(request, Method.PUT, clazz);
    }

    @Override
    public <T> BaseRequest delete(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz){
        Object request = buildRequest(url, paths, queries, headers, null);
        return doRequest(request, Method.DELETE, clazz);
    }

    public abstract Object buildRequest(String url, Map<String, String> paths, Map<String, String> queries, Map<String,
            String> headers, Object jsonObject);

    public abstract <T> BaseRequest doRequest(Object response, Method method, Class<T> clazz);
}
