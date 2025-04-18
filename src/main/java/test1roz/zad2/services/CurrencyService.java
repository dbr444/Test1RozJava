package test1roz.zad2.services;

import static test1roz.zad2.util.KeyUtils.currencyKey;
import static test1roz.zad2.util.ValidationUtils.validateCurrencyInputs;

import test1roz.zad2.config.AppConfig;
import test1roz.zad2.models.RateAndResultWrapper;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyService implements ICurrencyService {

    private final IRateService rateService;
    private static final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    public CurrencyService(IRateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public BigDecimal exchange(String currencyFrom, String currencyTo, BigDecimal amount) {
        validateCurrencyInputs(currencyFrom, currencyTo, amount);

        String key = currencyKey(currencyFrom, currencyTo);
        Cache.CachedRate cached = Cache.getCachedRate(currencyFrom, currencyTo);

        if (isCacheValid(cached)) {
            return amount.multiply(cached.getRate());
        }

        Object lock = locks.computeIfAbsent(key, k -> new Object());

        synchronized (lock) {
            cached = Cache.getCachedRate(currencyFrom, currencyTo);
            if (isCacheValid(cached)) {
                return amount.multiply(cached.getRate());
            }

            return fetchAndCacheRateAndReturnResult(currencyFrom, currencyTo, amount);
        }
    }

    private boolean isCacheValid(Cache.CachedRate cached) {
        return cached != null && !cached.isExpired();
    }

    private BigDecimal fetchAndCacheRateAndReturnResult(String currencyFrom, String currencyTo, BigDecimal amount) {
        RateAndResultWrapper wrapper = rateService.fetchExchangeResultFromApi(currencyFrom, currencyTo, amount);
        Cache.putRateInCache(currencyFrom, currencyTo, wrapper.getRate(), AppConfig.CACHE_EXPIRATION_SECONDS);
        return wrapper.getResult();
    }
}
