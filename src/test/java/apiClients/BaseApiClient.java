package apiClients;

import entity.BaseRequest;

public interface BaseApiClient {

    <T>BaseRequest post(String scheme, String hostname, String path, T jsonObject);
    <T> BaseRequest get(String scheme, String hostname, String path, String query, Class<T> clazz);
    <T>BaseRequest put(String scheme, String hostname, String path, T jsonObject);
    <T>BaseRequest delete(String scheme, String hostname, String path, String query, Class<T> clazz);

}
