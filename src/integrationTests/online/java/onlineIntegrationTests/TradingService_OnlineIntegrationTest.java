package onlineIntegrationTests;

import org.junit.Before;
import org.junit.Test;
import service.TradingService;
import service.tools.BitsoApiRequester;

import java.net.URISyntaxException;

public class TradingService_OnlineIntegrationTest {
    private TradingService tradingService;
    private BitsoApiRequester bitsoApiRequester;

    @Before
    public void setUp() throws Exception {
        bitsoApiRequester = new BitsoApiRequester();
    }

    @Test
    public void shouldParseResultToTradeResult() throws URISyntaxException {
        // when
        tradingService = new TradingService(bitsoApiRequester);
        // then
    }
}
