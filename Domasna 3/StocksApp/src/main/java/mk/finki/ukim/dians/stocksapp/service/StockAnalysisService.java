package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;

public interface StockAnalysisService {
    BigDecimal calculateRSI(List<BigDecimal> prices, int period);
    BigDecimal calculateStochasticK(List<BigDecimal> prices, int period);
    BigDecimal calculateROC(List<BigDecimal> closingPrices, int period);
    BigDecimal calculateMomentum(List<BigDecimal> closingPrices, int period);
    BigDecimal calculateSMAOscillator(List<BigDecimal> closingPrices, int period);
    BigDecimal calculateCMO(List<BigDecimal> closingPrices, int period);
}
