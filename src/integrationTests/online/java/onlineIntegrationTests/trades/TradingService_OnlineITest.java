package onlineIntegrationTests.trades;

import org.junit.Before;
import org.junit.Test;
import service.trades.TradingService;
import service.trades._tools.rest_client.TradesRestApiClient;
import service.trades._tools.simulator.TradingSimulator;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TradingService_OnlineITest
{
    private TradingService tradingService;
    private TradesRestApiClient tradesRestApiClient;
    private TradingSimulator tradingSimulator;

    @Before
    public void setUp() throws Exception {
        tradesRestApiClient = new TradesRestApiClient();
        tradingSimulator = new TradingSimulator(3, 3);
    }

    @Test
    public void shouldParseResultToTradeResult() throws URISyntaxException {
        // when
        tradingService = TradingService.getInstance(tradesRestApiClient, tradingSimulator);
        // then
        assertNotNull(tradingService);
    }

    @Test
    public void shouldHaveInitialTrades() throws URISyntaxException {
        // when
        tradingService = TradingService.getInstance(tradesRestApiClient, tradingSimulator);
        // then
        assertEquals(100, tradingService.getLastTrades(100).size());
    }
}
