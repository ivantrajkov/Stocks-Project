package mk.finki.ukim.dians.stocksapp.service;

import mk.finki.ukim.dians.stocksapp.model.StockData;

import java.util.List;

public interface csvService {
    public byte[] generateCSV(List<StockData> stockData);

}
