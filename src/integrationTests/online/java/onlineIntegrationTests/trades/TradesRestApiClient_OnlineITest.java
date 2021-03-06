package onlineIntegrationTests.trades;

import org.junit.Before;
import org.junit.Test;
import service.model.trades.TradeResult;
import service.trades._tools.rest_client.TradesRestApiClient;

import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TradesRestApiClient_OnlineITest
{
    private TradesRestApiClient tradesRestApiClient;

    @Before
    public void setUp() throws Exception {
        tradesRestApiClient = new TradesRestApiClient("https://api-dev.bitso.com/v3/trades?book=btc_mxn");
    }

    @Test
    public void shouldParseResultToTradeResult() throws URISyntaxException {
        // when
        TradeResult tradeResult = tradesRestApiClient.getTrades(25);
        // then
        assertNotNull(tradeResult);
        assertTrue(tradeResult.isSuccess());
        assertTrue(tradeResult.getTradeList().size() > 0);
    }

    @Test
    public void shouldReturnResultsAccordingToLimit() throws Exception {
        //given
        tradesRestApiClient = new TradesRestApiClient("https://api-dev.bitso.com/v3/trades?book=btc_mxn");
        // when
        TradeResult tradeResult = tradesRestApiClient.getTrades(5);
        // then
        assertEquals(5, tradeResult.getTradeList().size());
    }
}
