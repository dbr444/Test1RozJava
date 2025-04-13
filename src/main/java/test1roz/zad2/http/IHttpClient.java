package test1roz.zad2.http;

import java.io.InputStream;

public interface IHttpClient {
    InputStream get(String url) throws Exception;
}

