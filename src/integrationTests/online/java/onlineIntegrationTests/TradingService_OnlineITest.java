package onlineIntegrationTests;

import org.junit.Before;
import org.junit.Test;
import service.trades.TradesApiClient;
import service.trades.TradingService;
import service.trades.TradingSimulator;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TradingService_OnlineITest
{
    private TradingService tradingService;
    private TradesApiClient tradesApiClient;
    private TradingSimulator tradingSimulator;

    @Before
    public void setUp() throws Exception {
        tradesApiClient = new TradesApiClient();
        tradingSimulator = new TradingSimulator(3, 3);
    }

    @Test
    public void shouldParseResultToTradeResult() throws URISyntaxException {
        // when
        tradingService = new TradingService(tradesApiClient, tradingSimulator);
        // then
        assertNotNull(tradingService);
    }

    @Test
    public void shouldHaveInitialTrades() throws URISyntaxException {
        // when
        tradingService = new TradingService(tradesApiClient, tradingSimulator);
        // then
        assertEquals(100, tradingService.getLastTrades(100).size());
    }
}
