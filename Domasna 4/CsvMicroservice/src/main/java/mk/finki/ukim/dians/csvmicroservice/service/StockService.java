package mk.finki.ukim.dians.csvmicroservice.service;


import mk.finki.ukim.dians.csvmicroservice.model.StockData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StockService {
    /**
     * Retrieves all stock data from the repository.
     *
     * @return a list of all StockData.
     */
    List<StockData> getAllStockData();
//    Optional<StockData> getStockDataById(Long id);
    /**
     * Retrieves stock data based on the provided filters.
     * @param symbol the stock symbol to filter by.
     * @param avgPrice the average price to filter by.
     * @param fromDate the start date of the range to filter by.
     * @param toDate the end date of the range to filter by.
     * @return a list of StockData that matches the filters.
     */
    List<StockData> getFilteredStockData(String symbol, BigDecimal avgPrice, String fromDate, String toDate);
    /**
     * Retrieves all stock data for a specific stock symbol.
     *
     * @param symbol the stock symbol to filter by.
     * @return a list of StockData for the given symbol.
     */
    List<StockData> getByStockSymbol(String symbol);
    /**
     * Retrieves a list of distinct stock symbols from the repository.
     *
     * @return a list of distinct stock symbols.
     */
    List<String> findDistinctStockSymbols();
    /**
     * Extracts the last transaction prices from a list of StockData objects.
     *
     * @param stockDataList the list of StockData to extract prices from.
     * @return a list of BigDecimal values representing the last transaction prices.
     */
    List<BigDecimal> getLastTransactionPrices(List<StockData> stockDataList);
    /**
     * Calculates the average price for each year from a list of StockData objects.
     *
     * @param listData the list of StockData to process.
     * @return a map where the key is the year and the value is the average price for that year.
     */
    Map<String, Double> getYearAverage(List<StockData> listData);


}
