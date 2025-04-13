package test1roz.zad2.services;
import static test1roz.zad2.util.ValidationUtils.validateCurrencyInputs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import test1roz.zad2.exceptions.CurrencyExchangeException;
import test1roz.zad2.http.IHttpClient;
import test1roz.zad2.models.RateAndResultWrapper;

import java.io.InputStream;
import java.math.BigDecimal;

public class RateService implements IRateService {
    private final ObjectMapper objectMapper;
    private final IUrlStringBuilder urlBuilder;
    private final IHttpClient httpClient;

    public RateService(ObjectMapper objectMapper, IUrlStringBuilder urlBuilder, IHttpClient httpClient) {
        this.objectMapper = objectMapper;
        this.urlBuilder = urlBuilder;
        this.httpClient = httpClient;
    }

    @Override
    public RateAndResultWrapper fetchExchangeResultFromApi(String from, String to, BigDecimal amount) {
        validateCurrencyInputs(from, to, amount);
        try {
            String urlStr = urlBuilder.build(from, to, amount);
            try (InputStream is = httpClient.get(urlStr)) {
                JsonNode root = objectMapper.readTree(is);
                return extractRateAndResult(root);
            }
        } catch (Exception e) {
            throw new CurrencyExchangeException("Failed to fetch rate/result from API!");
        }
    }

    private RateAndResultWrapper extractRateAndResult(JsonNode root) {
        BigDecimal rate = new BigDecimal(root.get("info").get("rate").asText());
        BigDecimal result = new BigDecimal(root.get("result").asText());
        return new RateAndResultWrapper(rate, result);
    }
}