package test1roz.zad2.http;

import test1roz.zad2.config.AppConfig;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientImpl implements IHttpClient {

    @Override
    public InputStream get(String urlStr) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlStr).openConnection();
        connection.setRequestProperty("apikey", AppConfig.API_KEY);
        connection.setRequestMethod("GET");
        return connection.getInputStream();
    }
}

