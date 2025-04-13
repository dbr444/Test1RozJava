package test1roz.zad2.services;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import test1roz.zad2.config.AppConfig;
import test1roz.zad2.exceptions.CurrencyExchangeException;
import test1roz.zad2.models.RateAndResultWrapper;
import java.math.BigDecimal;

public class CurrencyServiceTest {

    private IRateService rateServiceMock;
    private CurrencyService currencyService;
    private String currencyEur;
    private String currencyUsd;
    private String currencyPln;

    @Before
    public void setUp() {
        rateServiceMock = mock(IRateService.class);
        currencyService = new CurrencyService(rateServiceMock);
        currencyEur = "EUR";
        currencyUsd = "USD";
        currencyPln = "PLN";
    }

    @Test
    public void shouldReturnConvertedAmountFromApiWhenCacheIsEmpty() {
        Cache.clearCache();
        BigDecimal amount = new BigDecimal("50");
        RateAndResultWrapper expectedResult = new RateAndResultWrapper(new BigDecimal("1.20"), new BigDecimal("60.00"));
        when(rateServiceMock.fetchExchangeResultFromApi(currencyEur, currencyUsd, amount))
                .thenReturn(expectedResult);

        BigDecimal exchangedValue = currencyService.exchange(currencyEur, currencyUsd, new BigDecimal("50"));

        assertThat(exchangedValue).isEqualByComparingTo("60.00");
        verify(rateServiceMock, times(1)).fetchExchangeResultFromApi(currencyEur, currencyUsd, amount);
    }

    @Test
    public void shouldUseCacheAndNotCallApiWhenRateIsCached() {
        Cache.clearCache();
        Cache.putRateInCache(currencyEur, currencyPln, new BigDecimal("2.00"), 5);

        BigDecimal result = currencyService.exchange(currencyEur, currencyPln, new BigDecimal("30"));

        assertThat(result).isEqualByComparingTo("60.00");

        verify(rateServiceMock, times(0)).fetchExchangeResultFromApi(currencyEur, currencyPln, new BigDecimal("50"));
    }

    @Test
    public void shouldRefreshRateAfterCacheExpires() throws InterruptedException {
        BigDecimal amount = new BigDecimal("100");
        RateAndResultWrapper firstResult = new RateAndResultWrapper(new BigDecimal("2.00"), new BigDecimal("200.00"));
        RateAndResultWrapper secondResult = new RateAndResultWrapper(new BigDecimal("3.00"), new BigDecimal("300.00"));

        when(rateServiceMock.fetchExchangeResultFromApi(currencyEur, currencyUsd, amount))
                .thenReturn(firstResult)
                .thenReturn(secondResult);

        BigDecimal firstCall = currencyService.exchange(currencyEur, currencyUsd, amount);
        assertThat(firstCall).isEqualByComparingTo(firstResult.getResult());

        Thread.sleep((AppConfig.CACHE_EXPIRATION_SECONDS + 1) * 1000L);

        BigDecimal secondCall = currencyService.exchange(currencyEur, currencyUsd, amount);
        assertThat(secondCall).isEqualByComparingTo(secondResult.getResult());

        verify(rateServiceMock, times(2)).fetchExchangeResultFromApi(currencyEur, currencyUsd, amount);
    }


    @Test
    public void shouldThrowExceptionForInvalidParameters() {
        assertThatThrownBy(() -> currencyService.exchange(null, currencyUsd, BigDecimal.TEN))
                .isInstanceOf(CurrencyExchangeException.class);

        assertThatThrownBy(() -> currencyService.exchange(currencyEur, null, BigDecimal.TEN))
                .isInstanceOf(CurrencyExchangeException.class);

        assertThatThrownBy(() -> currencyService.exchange(currencyEur, currencyUsd, null))
                .isInstanceOf(CurrencyExchangeException.class);

        assertThatThrownBy(() -> currencyService.exchange(currencyEur, currencyUsd, BigDecimal.ZERO))
                .isInstanceOf(CurrencyExchangeException.class);
    }
}
