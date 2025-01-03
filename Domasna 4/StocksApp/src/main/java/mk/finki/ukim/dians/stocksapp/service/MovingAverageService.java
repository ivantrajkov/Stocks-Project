package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.*;

public interface MovingAverageService {
    String getSimpleMovingAverageSignal(BigDecimal currentPrice, BigDecimal sma);
    String getExponentialMovingAverageSignal(BigDecimal currentPrice, BigDecimal ema);
    String getWeightedMovingAverageSignal(BigDecimal currentPrice, BigDecimal wma);
    String getTriangularMovingAverageSignal(BigDecimal currentPrice, BigDecimal tma);
    String getKaufmanAdaptiveMovingAverageSignal(BigDecimal currentPrice, BigDecimal kama);
    String combineSignals(List<String> signals);
}
