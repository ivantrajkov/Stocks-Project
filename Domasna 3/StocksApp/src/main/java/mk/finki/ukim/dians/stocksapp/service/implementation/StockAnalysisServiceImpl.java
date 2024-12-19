package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.StockAnalysisService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

        List<BigDecimal> recentPrices = closingPrices.subList(closingPrices.size() - period, closingPrices.size());

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal price : recentPrices) {
            sum = sum.add(price);
        }

        return sum.divide(BigDecimal.valueOf(period), RoundingMode.HALF_UP);
    }




    @Override
    public BigDecimal calculateCMO(List<BigDecimal> closingPrices, int period) {
        if (closingPrices == null || closingPrices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate CMO for the given period.");
        }

        BigDecimal sumGains = BigDecimal.ZERO;
        BigDecimal sumLosses = BigDecimal.ZERO;

        for (int i = closingPrices.size() - period; i < closingPrices.size() - 1; i++) {
            BigDecimal change = closingPrices.get(i + 1).subtract(closingPrices.get(i));

            if (change.compareTo(BigDecimal.ZERO) > 0) {
                sumGains = sumGains.add(change);
            } else {
                sumLosses = sumLosses.add(change.abs());
            }
        }

        BigDecimal cmo = sumGains.subtract(sumLosses)
                .divide(sumGains.add(sumLosses), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return cmo;
    }

    @Override
    public BigDecimal calculateEMA(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < period) {
            throw new IllegalArgumentException("Not enough data points to calculate EMA.");
        }
        BigDecimal multiplier = BigDecimal.valueOf(2.0 / (period + 1));
        BigDecimal ema = calculateSMAOscillator(prices.subList(0, period), period);
        for (int i = period; i < prices.size(); i++) {
            ema = prices.get(i).subtract(ema).multiply(multiplier).add(ema);
        }
        return ema.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateWMA(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < period) {
            throw new IllegalArgumentException("Not enough data points to calculate WMA.");
        }
        BigDecimal weightedSum = BigDecimal.ZERO;
        int weight = 0;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            weight += (i - (prices.size() - period) + 1);
            weightedSum = weightedSum.add(prices.get(i).multiply(BigDecimal.valueOf(i - (prices.size() - period) + 1)));
        }
        return weightedSum.divide(BigDecimal.valueOf(weight), RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTMA(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < 2 * period) {
            throw new IllegalArgumentException("Not enough data points to calculate TMA. Ensure the list has at least " + (2 * period) + " data points.");
        }

        List<BigDecimal> firstSMAValues = new ArrayList<>();
        for (int i = 0; i <= prices.size() - period; i++) {
            List<BigDecimal> subList = prices.subList(i, i + period);
            firstSMAValues.add(calculateSMAOscillator(subList, period));
        }

        List<BigDecimal> secondSMAValues = new ArrayList<>();
        for (int i = 0; i <= firstSMAValues.size() - period; i++) {
            List<BigDecimal> subList = firstSMAValues.subList(i, i + period);
            secondSMAValues.add(calculateSMAOscillator(subList, period));
        }

        return secondSMAValues.get(secondSMAValues.size() - 1);
    }


    @Override
    public BigDecimal calculateKAMA(List<BigDecimal> prices, int period) {
        if (prices == null || prices.size() < period + 1) {
            throw new IllegalArgumentException("Not enough data points to calculate KAMA.");
        }

        BigDecimal sumPriceChanges = BigDecimal.ZERO;
        BigDecimal absolutePriceChange = prices.get(prices.size() - 1).subtract(prices.get(prices.size() - period));

        for (int i = prices.size() - period; i < prices.size() - 1; i++) {
            sumPriceChanges = sumPriceChanges.add(prices.get(i + 1).subtract(prices.get(i)).abs());
        }

        BigDecimal efficiencyRatio = sumPriceChanges.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : absolutePriceChange.abs().divide(sumPriceChanges, RoundingMode.HALF_UP);

        BigDecimal fastestConstant = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(period + 1), RoundingMode.HALF_UP);
        BigDecimal slowestConstant = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(period * 2 + 1), RoundingMode.HALF_UP);

        BigDecimal smoothingConstant = efficiencyRatio.multiply(fastestConstant.subtract(slowestConstant)).add(slowestConstant);

        BigDecimal kama = prices.get(prices.size() - period);

        for (int i = prices.size() - period + 1; i < prices.size(); i++) {
            BigDecimal price = prices.get(i);
            kama = kama.add(smoothingConstant.multiply(price.subtract(kama)));
        }

        return kama.setScale(2, RoundingMode.HALF_UP);
    }


}
