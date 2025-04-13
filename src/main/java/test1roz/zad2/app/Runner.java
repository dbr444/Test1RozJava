package test1roz.zad2.app;

import test1roz.zad2.http.HttpClientImpl;
import test1roz.zad2.http.IHttpClient;
import test1roz.zad2.services.*;
import test1roz.zad2.util.ObjectMapperHolder;

import java.math.BigDecimal;

public class Runner {
    public static void main(String[] args) {
        IUrlStringBuilder urlBuilder = new ApilayerUrlStringBuilder();
        IHttpClient httpClient = new HttpClientImpl();
        IRateService rateService = new RateService(
                ObjectMapperHolder.INSTANCE.getObjectMapper(),
                urlBuilder,
                httpClient
        );
        ICurrencyService currencyService = new CurrencyService(rateService);

        BigDecimal amount = new BigDecimal("50");

        long start1 = System.nanoTime();
        BigDecimal result = currencyService.exchange("EUR", "USD", amount);
        System.out.println("EUR : USD: " + result);
        System.out.println("API call time: " + (System.nanoTime() - start1) / 1_000_000 + " ms");

        System.out.println("--------------------");

        long start2 = System.nanoTime();
        BigDecimal secondResult = currencyService.exchange("EUR", "USD", amount);
        System.out.println("From cache EUR : USD: " + secondResult);
        System.out.println("Cache call time: " + (System.nanoTime() - start2) / 1_000_000 + " ms");
    }
}
