package test1roz.zad2.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static final ConcurrentHashMap<String, CachedRate> cache = new ConcurrentHashMap<>();

    public static CachedRate getCachedRate(String currencyFrom, String currencyTo) {
        String cacheKey = currencyFrom + ":" + currencyTo;
        return cache.get(cacheKey);
    }

    public static void putRateInCache(String currencyFrom, String currencyTo, BigDecimal rate, long ttlInSeconds) {
        String cacheKey = currencyFrom + ":" + currencyTo;
        cache.put(cacheKey, new CachedRate(rate, Instant.now().plusSeconds(ttlInSeconds)));
    }

    public static void clearCache() {
        cache.clear();
    }

    public static class CachedRate {
        private final BigDecimal rate;
        private final Instant expiresAt;

        public CachedRate(BigDecimal rate, Instant expiresAt) {
            this.rate = rate;
            this.expiresAt = expiresAt;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public boolean isExpired() {
            return Instant.now().isAfter(expiresAt);
        }
    }
}

