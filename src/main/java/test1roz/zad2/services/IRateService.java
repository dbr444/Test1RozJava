package test1roz.zad2.services;

import test1roz.zad2.models.RateAndResultWrapper;
import java.math.BigDecimal;

public interface IRateService {
    RateAndResultWrapper fetchExchangeResultFromApi(String from, String to, BigDecimal amount);
}
