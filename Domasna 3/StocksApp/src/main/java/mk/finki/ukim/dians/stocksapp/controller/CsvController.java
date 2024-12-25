package mk.finki.ukim.dians.stocksapp.controller;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.service.StockService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/csv")
@Validated
@CrossOrigin(origins="*")
public class CsvController {
    private final StockService stockService;

    public CsvController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadCSV() {
        List<StockData> stockData = stockService.getAllStockData();

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.csv");
        headers.add("Content-Type", "text/csv");

        byte[] csvContent = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    }



}
