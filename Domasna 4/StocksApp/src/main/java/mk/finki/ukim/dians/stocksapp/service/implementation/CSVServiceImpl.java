package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.service.CSVService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {
    public byte[] generateCSV(List<StockData> stockData) {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID,Date,Last Transaction Price,Max Price,Min Price,Average Price,Percentage Change,Quantity,Best Turnover,Total Turnover,Stock Symbol\n");

        for (StockData stock : stockData) {
            csvBuilder.append(stock.getId()).append(",")
                    .append(stock.getDate()).append(",")
                    .append(stock.getLastTransactionPrice()).append(",")
                    .append(stock.getMaxPrice()).append(",")
                    .append(stock.getMinPrice()).append(",")
                    .append(stock.getAveragePrice()).append(",")
                    .append(stock.getPercentageChange()).append(",")
                    .append(stock.getQuantity()).append(",")
                    .append(stock.getBestTurnover()).append(",")
                    .append(stock.getTotalTurnover()).append(",")
                    .append(stock.getStockSymbol()).append("\n");
        }

        return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
