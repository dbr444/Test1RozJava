package test1roz.zad2.services;

import test1roz.zad2.exceptions.CurrencyExchangeException;
import java.math.BigDecimal;

public interface ICurrencyService {
    BigDecimal exchange(String currencyFrom, String currencyTo, BigDecimal amount) throws CurrencyExchangeException;
}