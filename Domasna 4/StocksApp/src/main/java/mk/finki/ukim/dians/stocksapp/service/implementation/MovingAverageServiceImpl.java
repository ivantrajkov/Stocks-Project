package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.MovingAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovingAverageServiceImpl implements MovingAverageService {
    private String getSignal(BigDecimal currentPrice, BigDecimal movingAverageValue) {
        return currentPrice.compareTo(movingAverageValue) > 0 ? "Buy" : "Sell";
    }
    @Override
    public String getSimpleMovingAverageSignal(BigDecimal currentPrice, BigDecimal sma) {
        return getSignal(currentPrice, sma);
    }

    @Override
    public String getExponentialMovingAverageSignal(BigDecimal currentPrice, BigDecimal ema) {
        return getSignal(currentPrice, ema);
    }

    @Override
    public String getWeightedMovingAverageSignal(BigDecimal currentPrice, BigDecimal wma) {
        return getSignal(currentPrice, wma);
    }

    @Override
    public String getTriangularMovingAverageSignal(BigDecimal currentPrice, BigDecimal tma) {
        return getSignal(currentPrice, tma);
    }

    @Override
    public String getKaufmanAdaptiveMovingAverageSignal(BigDecimal currentPrice, BigDecimal kama) {
        return getSignal(currentPrice, kama);
    }

    @Override
    public String combineSignals(List<String> signals) {
        int buyCount = 0;
        int sellCount = 0;

        for (String signal : signals) {
            if (signal.equals("Buy")) {
                buyCount++;
            } else if (signal.equals("Sell")) {
                sellCount++;
            }
        }
        if (buyCount > sellCount) {
            return "Buy";
        } else if (sellCount > buyCount) {
            return "Sell";
        } else {
            return "Neutral";
        }
    }
}
