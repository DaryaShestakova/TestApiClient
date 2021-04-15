package apiClients;

import entity.BaseRequest;

import java.util.Map;

public interface BaseApiClient{

    <T, K> BaseRequest post(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers,
                         T jsonObject, Class<K> clazz);
    <T> BaseRequest get(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz);
    <T, K> BaseRequest put(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, T jsonObject, Class<K> clazz);
    <T> BaseRequest delete(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz);

}
