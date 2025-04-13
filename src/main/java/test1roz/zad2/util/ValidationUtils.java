package test1roz.zad2.util;

import test1roz.zad2.exceptions.CurrencyExchangeException;
import java.math.BigDecimal;

public class ValidationUtils {

    public static void validateCurrencyInputs(String from, String to, BigDecimal amount) {
        if (from == null || to == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CurrencyExchangeException("Invalid input parameters.");
        }
    }
}
