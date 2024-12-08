package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;

public interface StockAnalysisService {
    BigDecimal calculateRSI(List<BigDecimal> prices, int period);
}
