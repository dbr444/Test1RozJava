package test1roz.zad2.services;

import java.math.BigDecimal;

import static test1roz.zad2.config.AppConfig.*;

public class ApilayerUrlStringBuilder implements IUrlStringBuilder {
    @Override
    public String build(String currencyFrom, String currencyTo, BigDecimal  amount) {
        return API_URL + CONVERT_ENDPOINT + currencyTo + FROM_PARAMETER + currencyFrom + AMOUNT_PARAMETER + amount;
    }
}