package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;

public interface StockAnalysisService {
    BigDecimal calculateRSIForPeriod(List<BigDecimal> prices, int period);
    public BigDecimal calculateRSI(List<BigDecimal> prices);
}
