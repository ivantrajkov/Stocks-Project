package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.repository.StockRepository;
import mk.finki.ukim.dians.stocksapp.service.StockService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;


    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<StockData> getAllStockData() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<StockData> getStockDataById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public List<StockData> getFilteredStockData(String symbol, BigDecimal avgPrice , String fromDate, String toDate)  {
        return stockRepository.getFilteredStockData(symbol, avgPrice, fromDate, toDate);
    }

    @Override
    public List<StockData> getByStockSymbol(String symbol) {
        return stockRepository.getByStockSymbol(symbol);
    }

    @Override
    public List<String> findDistinctStockSymbols() {
        return stockRepository.findDistinctStockSymbols();
    }
}
