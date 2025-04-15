package test1roz.zad2.services;

import static test1roz.zad2.util.ValidationUtils.validateCurrencyInputs;
import test1roz.zad2.config.AppConfig;
import test1roz.zad2.models.RateAndResultWrapper;
import java.math.BigDecimal;

public class CurrencyService implements ICurrencyService {

    private final IRateService rateService;

    public CurrencyService(IRateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public BigDecimal exchange(String currencyFrom, String currencyTo, BigDecimal amount) {
        validateCurrencyInputs(currencyFrom, currencyTo, amount);

        Cache.CachedRate cached = Cache.getCachedRate(currencyFrom, currencyTo);
        if (cached != null && !cached.isExpired()) {
            return amount.multiply(cached.getRate());
        }

        String key = (currencyFrom + ":" + currencyTo).intern();

        synchronized (key) {
            cached = Cache.getCachedRate(currencyFrom, currencyTo);
            if (cached != null && !cached.isExpired()) {
                return amount.multiply(cached.getRate());
            }

            RateAndResultWrapper wrapper = rateService.fetchExchangeResultFromApi(currencyFrom, currencyTo, amount);
            Cache.putRateInCache(currencyFrom, currencyTo, wrapper.getRate(), AppConfig.CACHE_EXPIRATION_SECONDS);
            return wrapper.getResult();
        }
    }
}
