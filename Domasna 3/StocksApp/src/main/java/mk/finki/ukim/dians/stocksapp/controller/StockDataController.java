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
    public ResponseEntity<BigDecimal> getRsi(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = listData.stream()
                .map(StockData::getLastTransactionPrice)
                .collect(Collectors.toList());

        BigDecimal rsiIndex = stockAnalysisService.calculateRSI(prices,period);

        return new ResponseEntity<>(rsiIndex,HttpStatus.OK);
    }

    @GetMapping("/stochastic")
    public ResponseEntity<BigDecimal> getStochastic(
            @RequestParam String symbol,
            @RequestParam int period
    ) {
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = listData.stream()
                .map(StockData::getLastTransactionPrice)
                .collect(Collectors.toList());
        BigDecimal stochasticK = stockAnalysisService.calculateStochasticK(prices,period);
        return new ResponseEntity<>(stochasticK,HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestParam String username,
            @RequestParam String password
    ) {
        User user = new User(username, password);
        userService.addUser(user);
        Map<String, String> response = new HashMap<>();
        response.put("redirect", "/login");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        Optional<User> userOpt = Optional.ofNullable(userService.isLoggedIn());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            user.setLoggedIn(false);
            userService.addUser(user);
        }
        return userService.getByUsername(username)
                .map(user -> {
                    if (user.getPassword().equals(password)) {
                        user.setLoggedIn(true);
                        userService.addUser(user);
                        return ResponseEntity.ok("Login successful");
                    }
                    return ResponseEntity.badRequest().body("Invalid password");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @GetMapping("/loggedIn")
    public ResponseEntity<String> getLoggedInfo(){
        User user = userService.isLoggedIn();
        if(user == null){
            return new ResponseEntity<>("Guest",HttpStatus.OK);
        } else {

        }return new ResponseEntity<>(user.getUsername(),HttpStatus.OK);
    }

}
