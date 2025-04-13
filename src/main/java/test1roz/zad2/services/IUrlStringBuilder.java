package test1roz.zad2.services;

import java.math.BigDecimal;

public interface IUrlStringBuilder {
    String build(String currencyFrom, String currencyTo, BigDecimal amount);
}
