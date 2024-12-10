package mk.finki.ukim.dians.stocksapp.controller;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.model.User;
import mk.finki.ukim.dians.stocksapp.repository.StockRepository;
import mk.finki.ukim.dians.stocksapp.service.StockAnalysisService;
import mk.finki.ukim.dians.stocksapp.service.StockService;
import mk.finki.ukim.dians.stocksapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
@Validated
@CrossOrigin(origins="*")

public class StockDataController {
    private final StockService stockService;
    private final StockRepository stockRepository;
    private final StockAnalysisService stockAnalysisService;
    private final UserService userService;

    public StockDataController(StockService stockService, StockRepository stockRepository, StockAnalysisService stockAnalysisService, UserService userService) {
        this.stockService = stockService;
        this.stockRepository = stockRepository;
        this.stockAnalysisService = stockAnalysisService;
        this.userService = userService;
    }

@GetMapping("/all")
public ResponseEntity<List<StockData>> listAll(
        @RequestParam(required = false) String symbol,
        @RequestParam(required = false) String avgPrice,
        @RequestParam(required = false) String fromDate,
        @RequestParam(required = false) String toDate
){
    List<StockData> listData;
    if (symbol != null && symbol.isEmpty()) symbol = null;
    if (avgPrice != null && avgPrice.isEmpty()) avgPrice = null;
    if (fromDate != null && fromDate.isEmpty()) fromDate = null;
    if (toDate != null && toDate.isEmpty()) toDate = null;

    if (symbol != null || avgPrice != null || fromDate != null || toDate != null) {

        BigDecimal average = null;

        if (avgPrice != null && !avgPrice.isEmpty()) {
            if (avgPrice.contains(",")) {
                avgPrice = avgPrice.replace(",", ".");
            }
            average = new BigDecimal(avgPrice);
        }

        listData = stockService.getFilteredStockData(symbol, average, fromDate, toDate);
    } else {
        listData = stockService.getAllStockData();
    }

    return new ResponseEntity<>(listData, HttpStatus.OK);
    }

    @GetMapping("/graph")
    public ResponseEntity<Map<String, Double>> getTmp(@RequestParam String symbol) {

        List<StockData> listData = stockService.getByStockSymbol(symbol);

        Map<String, Double> averageByYear = listData.stream()
                .map(s -> {
                    String year = s.getDate().substring(0, 4);
                    s.setDate(year);
                    return s;
                })
                .collect(Collectors.groupingBy(
                        StockData::getDate,
                        Collectors.averagingDouble(s -> Optional.ofNullable(s.getAveragePrice()).map(BigDecimal::doubleValue).orElse(0.0))
                ));
        System.out.println(averageByYear);

        return new ResponseEntity<>(averageByYear, HttpStatus.OK);
    }
    @GetMapping("/stocks")
    public ResponseEntity<List<String>> getStocksSymbols(
    ){
        List<String> stockSymbols = stockService.findDistinctStockSymbols();
        return new ResponseEntity<>(stockSymbols,HttpStatus.OK);

    }
    @GetMapping("/rsi")
    public ResponseEntity<BigDecimal> getRsi(@RequestParam String symbol){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = listData.stream()
                .map(StockData::getLastTransactionPrice)
                .collect(Collectors.toList());
        prices.forEach(System.out::println);
        BigDecimal rsiIndex = stockAnalysisService.calculateRSI(prices);

        return new ResponseEntity<>(rsiIndex,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String username,
            @RequestParam String password
    ){
        User user = new User(username, password);
        userService.addUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}