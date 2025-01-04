package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * This service is used to generate the signals based on the values of each oscillator calculated beforehand.
 * For each oscillator value we send, it generates a signal, such as "Buy","Neutral", "Sell".
 * Also it can combine the signals of all oscillators to get the overall signal as
 * "Buy","Neutral", "Sell".
 */
public interface OscillatorSignalService {
    /**
     * Generates a trading signal based on the Relative Strength Index (RSI).
     *
     * @param rsi The RSI value to evaluate.
     * @return A trading signal as a String: "Strong Buy", "Buy", "Neutral", or "Sell".
     */
    String getRsiSignal(BigDecimal rsi);
    /**
     * Generates a trading signal based on the Stochastic Oscillator.
     *
     * @param stochastic The Stochastic value to evaluate.
     * @return A trading signal as a String: "Strong Buy", "Buy", "Neutral", or "Sell".
     */
    String getStochasticSignal(BigDecimal stochastic);
    /**
     * Generates a trading signal based on the Rate of Change (ROC).
     *
     * @param roc The ROC value to evaluate.
     * @return A trading signal as a String: "Buy" or "Sell".
     */
    String getRateOfChangeSignal(BigDecimal roc);
    /**
     * Generates a trading signal based on the Momentum.
     *
     * @param momentum The Momentum value to evaluate.
     * @return A trading signal as a String: "Buy" or "Sell".
     */
    String getMomentumSignal(BigDecimal momentum);
    /**
     * Generates a trading signal based on the Chande Momentum Oscillator.
     *
     * @param chande The Chande value to evaluate.
     * @return A trading signal as a String: "Buy" or "Sell".
     */
    String getChandeSignal(BigDecimal chande);
    /**
     * Combines multiple individual signals into one general signal.
     *
     * @param signals A list of individual signals to combine.
     * @return A combined trading signal: "Buy", "Sell", or "Neutral".
     */
    String combineSignals(List<String> signals);
}
