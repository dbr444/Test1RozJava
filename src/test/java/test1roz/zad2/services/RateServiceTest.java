package test1roz.zad2.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import test1roz.zad2.exceptions.CurrencyExchangeException;
import test1roz.zad2.http.IHttpClient;
import test1roz.zad2.models.RateAndResultWrapper;
import test1roz.zad2.util.ObjectMapperHolder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RateServiceTest {

    private IUrlStringBuilder urlBuilderMock;
    private IHttpClient httpClientMock;
    private RateService rateService;
    private ObjectMapper mapper;
    private String json;
    private BigDecimal testAmount;
    private String url;
    private InputStream mockedStream;
    private String currencyFrom;
    private String currencyTo;

    @Before
    public void setUp() throws Exception {
        urlBuilderMock = mock(IUrlStringBuilder.class);
        httpClientMock = mock(IHttpClient.class);
        mapper = ObjectMapperHolder.INSTANCE.getObjectMapper();
        rateService = new RateService(mapper, urlBuilderMock, httpClientMock);
        json = new String(Files.readAllBytes(Paths.get("src/test/resources/response.json")));
        testAmount = new BigDecimal("50");
        url = "https://fake.url";
        mockedStream = new ByteArrayInputStream(json.getBytes());
        currencyFrom = "EUR";
        currencyTo = "USD";
    }

    @Test
    public void shouldReturnCorrectRateAndResultFromParsedJson() throws Exception {
        when(urlBuilderMock.build(currencyFrom, currencyTo, testAmount)).thenReturn(url);
        when(httpClientMock.get(url)).thenReturn(mockedStream);

        RateAndResultWrapper rateAndResult = rateService.fetchExchangeResultFromApi(currencyFrom, currencyTo, testAmount);

        assertThat(rateAndResult.getRate()).isEqualByComparingTo("1.135971");
        assertThat(rateAndResult.getResult()).isEqualByComparingTo("56.79855");

        verify(urlBuilderMock).build(currencyFrom, currencyTo, testAmount);
        verify(httpClientMock).get(url);
    }

    @Test(expected = CurrencyExchangeException.class)
    public void shouldThrowInvalidInputDataExceptionWhenHttpClientFails() throws Exception {
        when(urlBuilderMock.build(currencyFrom, currencyTo, testAmount)).thenReturn(url);
        when(httpClientMock.get(url)).thenThrow(new RuntimeException("Connection error"));

        rateService.fetchExchangeResultFromApi(currencyFrom, currencyTo, testAmount);
    }

}
