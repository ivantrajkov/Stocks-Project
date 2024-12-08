package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.StockAnalysisService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StockAnalysisServiceImpl implements StockAnalysisService {
    @Override
    public BigDecimal calculateRSI(List<BigDecimal> prices, int period) {
//        if (prices == null || prices.isEmpty()) {
//            throw new IllegalArgumentException("Price list cannot be null or empty.");
//        }
//
//        if (period <= 0) {
//            throw new IllegalArgumentException("Period must be a positive integer.");
//        }
//
//        if (prices.size() < period + 1) {
//            throw new IllegalArgumentException("Not enough data points to calculate RSI for the given period.");
//        }

        BigDecimal gain = BigDecimal.ZERO;
        BigDecimal loss = BigDecimal.ZERO;


        for (int i = 1; i <= period; i++) {
            BigDecimal change = prices.get(i).subtract(prices.get(i - 1));
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                gain = gain.add(change);
            } else {
                loss = loss.add(change.abs());
            }
        }

        BigDecimal avgGain = gain.divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
        BigDecimal avgLoss = loss.divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);

        for (int i = period + 1; i < prices.size(); i++) {
            BigDecimal change = prices.get(i).subtract(prices.get(i - 1));
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                avgGain = avgGain.multiply(BigDecimal.valueOf(period - 1))
                        .add(change)
                        .divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
            } else {
                avgLoss = avgLoss.multiply(BigDecimal.valueOf(period - 1))
                        .add(change.abs())
                        .divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
            }
        }

        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(100);
        }

        BigDecimal rs = avgGain.divide(avgLoss, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(100)
                .subtract(BigDecimal.valueOf(100)
                        .divide(BigDecimal.ONE.add(rs), RoundingMode.HALF_UP));
    }
}
