package test1roz.zad2.models;

import java.math.BigDecimal;

public class RateAndResultWrapper {
    private final BigDecimal rate;
    private final BigDecimal result;

    public RateAndResultWrapper(BigDecimal rate, BigDecimal result) {
        this.rate = rate;
        this.result = result;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getResult() {
        return result;
    }
}
