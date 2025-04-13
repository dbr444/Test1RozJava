package test1roz.zad2.config;

public interface AppConfig {
    String API_URL = "https://api.apilayer.com/exchangerates_data/";
    String CONVERT_ENDPOINT = "convert?to=";
    String FROM_PARAMETER = "&from=";
    String AMOUNT_PARAMETER = "&amount=";
    String API_KEY = "BnW7ENBiBLBknHJSmAIOb4xax9pdohFL";
    int CACHE_EXPIRATION_SECONDS = 10;
}
