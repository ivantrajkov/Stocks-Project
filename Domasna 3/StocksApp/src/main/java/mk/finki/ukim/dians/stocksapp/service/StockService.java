package mk.finki.ukim.dians.stocksapp.service;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.*;

public interface StockService {
    List<StockData> getAllStockData();
    Optional<StockData> getStockDataById(Long id);
    List<StockData> getFilteredStockData(String symbol, BigDecimal avgPrice, String fromDate, String toDate);
    List<StockData> getByStockSymbol(String symbol);
    List<String> findDistinctStockSymbols();
//    List<StockData> g


}
