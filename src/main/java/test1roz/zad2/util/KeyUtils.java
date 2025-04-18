package test1roz.zad2.util;

public class KeyUtils {
    private KeyUtils() {}

    public static String currencyKey(String currencyFrom, String currencyTo) {
        return currencyFrom + ":" + currencyTo;
    }
}

