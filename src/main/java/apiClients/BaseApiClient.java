package apiClients;

import entity.BaseRequest;

import java.util.Map;

public interface BaseApiClient{

    <T> BaseRequest post(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, T jsonObject, Class<T> clazz);
    <T> BaseRequest get(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz);
    <T> BaseRequest put(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, T jsonObject, Class<T> clazz);
    <T> BaseRequest delete(String url, Map<String, String> paths, Map<String, String> queries, Map<String, String> headers, Class<T> clazz);

}
