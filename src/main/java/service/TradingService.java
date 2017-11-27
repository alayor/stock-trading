package service;

import service.model.Trade;
import service.model.TradeResult;
import service.tools.BitsoApiRequester;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.Collections.reverse;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TradingService {

    private final CurrentTrades currentTrades;
    private final BitsoApiRequester bitsoApiRequester;
    private final ScheduledFuture<?> scheduledFuture;
    private final Runnable updateTradesRunnable = this::updateTrades;

    private static ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    public TradingService(BitsoApiRequester bitsoApiRequester) {
        this(bitsoApiRequester, getScheduledThreadPoolExecutor());
    }

    TradingService(BitsoApiRequester bitsoApiRequester, ScheduledExecutorService executor) {
        this.bitsoApiRequester = bitsoApiRequester;
        currentTrades = new CurrentTrades(getTradesFromApi(bitsoApiRequester), 3, 3);
        scheduledFuture = executor.scheduleWithFixedDelay(updateTradesRunnable, 5, 5, SECONDS);
    }

    private List<Trade> getTradesFromApi(BitsoApiRequester bitsoApiRequester) {
        List<Trade> tradeList = bitsoApiRequester.getTrades(100).getTradeList();
        reverse(tradeList);
        return tradeList;
    }

    void updateTrades() {
        TradeResult tradesSince = bitsoApiRequester.getTradesSince(currentTrades.getLastTradeId());
        currentTrades.addTrades(tradesSince.getTradeList());
    }

    Runnable getUpdateTradesRunnable() {
        return updateTradesRunnable;
    }

    public void stop() {
        scheduledFuture.cancel(false);
    }

    public List<Trade> getLastTrades() {
        return currentTrades.getTrades();
    }
}
