package mk.finki.ukim.dians.stocksapp.controller;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.service.StockService;
import mk.finki.ukim.dians.stocksapp.service.csvService;
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
    private final csvService csvService;

    public CsvController(StockService stockService, mk.finki.ukim.dians.stocksapp.service.csvService csvService) {
        this.stockService = stockService;
        this.csvService = csvService;
    }

    /**
     * Prepares the headers and the csv data for download
     */
    @GetMapping
    public ResponseEntity<byte[]> downloadCSV() {
        List<StockData> stockData = stockService.getAllStockData();

        byte[] csvContent = csvService.generateCSV(stockData);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.csv");
        headers.add("Content-Type", "text/csv");

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
    }



}
