package apiClients;

import org.apache.http.client.utils.URIBuilder;

public class URLCreator {

    public static String createURL(String scheme, String hostname, String path, String query) {
        URIBuilder builder = new URIBuilder()
                .setScheme(scheme)
                .setHost(hostname)
                .setPath((query == null) ? path : path + "/" + query);
        return builder.toString();
    }
}
