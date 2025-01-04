package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.*;

/**
 * This service is used to generate the signals based on the values of each moving average calculated beforehand.
 * For each moving average value we send, it generates a signal, such as "Buy","Neutral", "Sell".
 * Also, it can combine the signals of all moving averages to get the overall signal as
 * "Buy","Neutral", "Sell".
 */
public interface MovingAverageSignalService {
    /**
     * Returns a signal based on the comparison of the current price and the Simple Moving Average (SMA).
     *
     * The signal will be "Buy" if the current price is greater than the SMA, otherwise "Sell".
     *
     * @param currentPrice The current price of the asset.
     * @param sma The value of the Simple Moving Average (SMA).
     * @return The "Buy" or "Sell" signal.
     */
    String getSimpleMovingAverageSignal(BigDecimal currentPrice, BigDecimal sma);
    /**
     * Returns a signal based on the comparison of the current price and the Exponential Moving Average (EMA).
     *
     * The signal will be "Buy" if the current price is greater than the EMA, otherwise "Sell".
     *
     * @param currentPrice The current price of the asset.
     * @param ema The value of the Exponential Moving Average (EMA).
     * @return The "Buy" or "Sell" signal.
     */
    String getExponentialMovingAverageSignal(BigDecimal currentPrice, BigDecimal ema);
    /**
     * Returns a signal based on the comparison of the current price and the Weighted Moving Average (WMA).
     *
     * The signal will be "Buy" if the current price is greater than the WMA, otherwise "Sell".
     *
     * @param currentPrice The current price of the asset.
     * @param wma The value of the Weighted Moving Average (WMA).
     * @return The "Buy" or "Sell" signal.
     */
    String getWeightedMovingAverageSignal(BigDecimal currentPrice, BigDecimal wma);
    /**
     * Returns a signal based on the comparison of the current price and the Triangular Moving Average (TMA).
     *
     * The signal will be "Buy" if the current price is greater than the TMA, otherwise "Sell".
     *
     * @param currentPrice The current price of the asset.
     * @param tma The value of the Triangular Moving Average (TMA).
     * @return The "Buy" or "Sell" signal.
     */
    String getTriangularMovingAverageSignal(BigDecimal currentPrice, BigDecimal tma);
    /**
     * Returns a signal based on the comparison of the current price and the Kaufman Adaptive Moving Average (KAMA).
     *
     * The signal will be "Buy" if the current price is greater than the KAMA, otherwise "Sell".
     *
     * @param currentPrice The current price of the asset.
     * @param kama The value of the Kaufman Adaptive Moving Average (KAMA).
     * @return The "Buy" or "Sell" signal.
     */
    String getKaufmanAdaptiveMovingAverageSignal(BigDecimal currentPrice, BigDecimal kama);
    /**
     * Combines multiple moving average signals into a single signal.
     *
     * The method counts how many "Buy" and "Sell" signals it receives. If there are more "Buy" signals,
     * the final signal will be "Buy". If there are more "Sell" signals, the final signal will be "Sell".
     * If the counts are equal, it returns "Neutral".
     *
     * @param signals A list of moving average signals (either "Buy" or "Sell").
     * @return A single combined signal ("Buy", "Sell", or "Neutral").
     */
    String combineSignals(List<String> signals);
}
