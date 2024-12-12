package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.StockAnalysisService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class StockAnalysisServiceImpl implements StockAnalysisService {

    @Override
    public BigDecimal calculateRSI(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate RSI for the given period.");
        }


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

        for (int i = period; i < prices.size(); i++) {
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

    @Override
    public BigDecimal calculateStochasticK(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate Stochastic Oscillator for the given period.");
        }


        BigDecimal highestHigh = getHighestHigh(prices, period);
        BigDecimal lowestLow = getLowestLow(prices, period);
        BigDecimal currentClose = prices.get(prices.size() - 1);

        if (highestHigh.compareTo(lowestLow) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal stochasticK = currentClose.subtract(lowestLow)
                .divide(highestHigh.subtract(lowestLow), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return stochasticK;
    }

    private BigDecimal getHighestHigh(List<BigDecimal> prices, int period) {
        BigDecimal highestHigh = prices.get(prices.size() - period);
        for (int i = prices.size() - period + 1; i < prices.size(); i++) {
            if (prices.get(i).compareTo(highestHigh) > 0) {
                highestHigh = prices.get(i);
            }
        }
        return highestHigh;
    }


    private BigDecimal getLowestLow(List<BigDecimal> prices, int period) {
        BigDecimal lowestLow = prices.get(prices.size() - period);
        for (int i = prices.size() - period + 1; i < prices.size(); i++) {
            if (prices.get(i).compareTo(lowestLow) < 0) {
                lowestLow = prices.get(i);
            }
        }
        return lowestLow;
    }

    @Override
    public BigDecimal calculateROC(List<BigDecimal> closingPrices, int period) {
        if (closingPrices == null || closingPrices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate ROC for the given period.");
        }

        BigDecimal currentClose = closingPrices.get(closingPrices.size() - 1);
        BigDecimal pastClose = closingPrices.get(closingPrices.size() - 1 - period);

        if (pastClose.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return currentClose.subtract(pastClose)
                .divide(pastClose, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal calculateMomentum(List<BigDecimal> closingPrices, int period) {
        if (closingPrices == null || closingPrices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate Momentum for the given period.");
        }

        BigDecimal currentClose = closingPrices.get(closingPrices.size() - 1);
        BigDecimal pastClose = closingPrices.get(closingPrices.size() - 1 - period);

        return currentClose.subtract(pastClose);
    }

    @Override
    public BigDecimal calculateSMAOscillator(List<BigDecimal> closingPrices, int period) {
        if (closingPrices == null || closingPrices.size() < period) {
            throw new IllegalArgumentException("Not enough data points to calculate SMA Oscillator for the given period.");
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = closingPrices.size() - period; i < closingPrices.size(); i++) {
            sum = sum.add(closingPrices.get(i));
        }
        BigDecimal sma = sum.divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);

        BigDecimal currentClose = closingPrices.get(closingPrices.size() - 1);
        closingPrices = closingPrices.stream().limit(period).toList();
        System.out.println(closingPrices);

        return currentClose.subtract(sma);
    }

}
