package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.MovingAverageService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovingAverageServiceImpl implements MovingAverageService {
    @Override
    public String getSimpleMovingAverageSignal(BigDecimal currentPrice, BigDecimal sma) {
        return currentPrice.compareTo(sma) > 0 ? "Buy" : "Sell";
    }

    @Override
    public String getExponentialMovingAverageSignal(BigDecimal currentPrice, BigDecimal ema) {
        return currentPrice.compareTo(ema) > 0 ? "Buy" : "Sell";
    }

    @Override
    public String getWeightedMovingAverageSignal(BigDecimal currentPrice, BigDecimal wma) {
        return currentPrice.compareTo(wma) > 0 ? "Buy" : "Sell";
    }

    @Override
    public String getTriangularMovingAverageSignal(BigDecimal currentPrice, BigDecimal tma) {
        return currentPrice.compareTo(tma) > 0 ? "Buy" : "Sell";
    }

    @Override
    public String getKaufmanAdaptiveMovingAverageSignal(BigDecimal currentPrice, BigDecimal kama) {
        return currentPrice.compareTo(kama) > 0 ? "Buy" : "Sell";
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
