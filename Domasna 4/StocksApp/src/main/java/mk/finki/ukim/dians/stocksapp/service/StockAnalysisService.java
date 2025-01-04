package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;
/**
 * This service is used to perform the basic analysis of stocks,
 * in means of getting the basic index of the moving averages and oscillators, for a given period.
**/
public interface StockAnalysisService {
    /**
     * Calculates the Relative Strength Index (RSI) for a given list of prices and period.
     * RSI is a momentum oscillator that measures the speed and change of price movements.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the RSI.
     * @return The RSI value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateRSI(List<BigDecimal> prices, int period);
    /**
     * Calculates the Stochastic Oscillator %K value for a given list of prices and period.
     * The Stochastic Oscillator compares a particular closing price to a range of prices over a certain period.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the Stochastic Oscillator.
     * @return The Stochastic Oscillator %K value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateStochasticK(List<BigDecimal> prices, int period);
    /**
     * Calculates the Rate of Change (ROC) for a given list of closing prices and period.
     * ROC measures the percentage change in price between the current and a previous price.
     *
     * @param closingPrices List of closing prices.
     * @param period The number of periods to calculate the ROC.
     * @return The Rate of Change as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateROC(List<BigDecimal> closingPrices, int period);
    /**
     * Calculates the Momentum for a given list of closing prices and period.
     * Momentum measures the change in price between the current and a previous price.
     *
     * @param closingPrices List of closing prices.
     * @param period The number of periods to calculate Momentum.
     * @return The Momentum value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateMomentum(List<BigDecimal> closingPrices, int period);
    /**
     * Calculates the Simple Moving Average (SMA) Oscillator for a given list of closing prices and period.
     * The SMA Oscillator measures the average closing price over a set period.
     *
     * @param closingPrices List of closing prices.
     * @param period The number of periods to calculate the SMA Oscillator.
     * @return The SMA Oscillator value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateSMAOscillator(List<BigDecimal> closingPrices, int period);
    /**
     * Calculates the Chande Momentum Oscillator (CMO) for a given list of closing prices and period.
     * CMO measures the difference between the sum of gains and losses over a given period.
     *
     * @param closingPrices List of closing prices.
     * @param period The number of periods to calculate the CMO.
     * @return The CMO value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateCMO(List<BigDecimal> closingPrices, int period);
    /**
     * Calculates the Exponential Moving Average (EMA) for a given list of prices and period.
     * EMA gives more weight to recent prices, making it more responsive to new information.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the EMA.
     * @return The EMA value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateEMA(List<BigDecimal> prices, int period);
    /**
     * Calculates the Weighted Moving Average (WMA) for a given list of prices and period.
     * WMA gives more weight to recent prices based on the number of periods.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the WMA.
     * @return The WMA value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateWMA(List<BigDecimal> prices, int period);
    /**
     * Calculates the Triangular Moving Average (TMA) for a given list of prices and period.
     * TMA is a moving average of a moving average, resulting in a smoother curve.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the TMA.
     * @return The TMA value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateTMA(List<BigDecimal> prices, int period);
    /**
     * Calculates the Kaufman's Adaptive Moving Average (KAMA) for a given list of prices and period.
     * KAMA adapts to the volatility of the market by adjusting the smoothing factor.
     *
     * @param prices List of closing prices.
     * @param period The number of periods to calculate the KAMA.
     * @return The KAMA value as a BigDecimal.
     * @throws IllegalArgumentException If there are not enough data points.
     */
    BigDecimal calculateKAMA(List<BigDecimal> prices, int period);
}
