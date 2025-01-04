package mk.finki.ukim.dians.stocksapp.controller;

import mk.finki.ukim.dians.stocksapp.model.StockData;
import mk.finki.ukim.dians.stocksapp.model.User;
import mk.finki.ukim.dians.stocksapp.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
@Validated
@CrossOrigin(origins="*")

public class StockDataController {
    private final StockService stockService;
    private final StockAnalysisService stockAnalysisService;
    private final UserService userService;
    private final OscillatorSignalService oscillatorSignalService;
    private final MovingAverageSignalService movingAverageSignalService;

    public StockDataController(StockService stockService, StockAnalysisService stockAnalysisService, UserService userService, OscillatorSignalService oscillatorSignalService, MovingAverageSignalService movingAverageSignalService) {
        this.stockService = stockService;
        this.stockAnalysisService = stockAnalysisService;
        this.userService = userService;
        this.oscillatorSignalService = oscillatorSignalService;
        this.movingAverageSignalService = movingAverageSignalService;
    }

    /**
     *  Returns all the stock data
     */
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

    /**
     * Returns a map of the yeary averages for the stock by the symbol parameter,
     * it is used for drawing a graph on the frontend
     */
    @GetMapping("/graph")
    public ResponseEntity<Map<String, Double>> getYearlyAverages(@RequestParam String symbol) {

        List<StockData> listData = stockService.getByStockSymbol(symbol);
        Map<String, Double> averageByYear = stockService.getYearAverage(listData);

        return new ResponseEntity<>(averageByYear, HttpStatus.OK);
    }

    /**
     *  Returns all stock symbols
     */
    @GetMapping("/stocks")
    public ResponseEntity<List<String>> getStocksSymbols(
    ){
        List<String> stockSymbols = stockService.findDistinctStockSymbols();
        return new ResponseEntity<>(stockSymbols,HttpStatus.OK);

    }
    /**
     * Returns the RSI index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/rsi")
    public ResponseEntity<BigDecimal> getRsi(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        Collections.reverse(prices);

        BigDecimal rsiIndex = stockAnalysisService.calculateRSI(prices,period);

        return new ResponseEntity<>(rsiIndex,HttpStatus.OK);
    }

    /**
     Returns the stochacstic index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/stochastic")
    public ResponseEntity<BigDecimal> getStochastic(
            @RequestParam String symbol,
            @RequestParam int period
    ) {
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        Collections.reverse(prices);
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
        return new ResponseEntity<>(response,HttpStatus.OK);
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
                        return new ResponseEntity<>("Login successful",HttpStatus.OK);
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
    /**
     * Returns the ROC index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/roc")
    public ResponseEntity<BigDecimal> getRoc(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        Collections.reverse(prices);
        BigDecimal roc = stockAnalysisService.calculateROC(prices,period);
        return new ResponseEntity<>(roc,HttpStatus.OK);
    }

    /**
     * Returns the momentum index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/momentum")
    public ResponseEntity<BigDecimal> getMomentum(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal momentum = stockAnalysisService.calculateMomentum(prices,period);
        return new ResponseEntity<>(momentum,HttpStatus.OK);
    }

    /**
     * Returns the SMA index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/sma")
    public ResponseEntity<BigDecimal> getSimpleMovingAverage(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        Collections.reverse(prices);
        BigDecimal sma = stockAnalysisService.calculateSMAOscillator(prices,period);
        return new ResponseEntity<>(sma,HttpStatus.OK);
    }
    /**
     * Returns the CMO index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/cmo")
    public ResponseEntity<BigDecimal> getCMO(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal cmo = stockAnalysisService.calculateCMO(prices,period);
        return new ResponseEntity<>(cmo,HttpStatus.OK);
    }
    /**
     * Returns the EMA index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/ema")
    public ResponseEntity<BigDecimal> getEMA(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal ema = stockAnalysisService.calculateEMA(prices,period);
        return new ResponseEntity<>(ema,HttpStatus.OK);
    }

    /**
     * Returns the WMA index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/wma")
    public ResponseEntity<BigDecimal> getWMA(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal wma = stockAnalysisService.calculateWMA(prices,period);
        return new ResponseEntity<>(wma,HttpStatus.OK);
    }

    /**
     *     Returns the TMA index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/tma")
    public ResponseEntity<BigDecimal> getTMA(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal tma = stockAnalysisService.calculateTMA(prices,period);
        return new ResponseEntity<>(tma,HttpStatus.OK);
    }
    /**
     * Returns the KAMA index for a stock symbol and the period specified by the period parameter
     */
    @GetMapping("/kama")
    public ResponseEntity<BigDecimal> getKAMA(
            @RequestParam String symbol,
            @RequestParam int period
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);

        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);
        Collections.reverse(prices);
        BigDecimal tma = stockAnalysisService.calculateKAMA(prices,period);
        return new ResponseEntity<>(tma,HttpStatus.OK);
    }

    /**
     * For each parameter sent as an oscillator value, it calls the oscillator service
     * to get the signal if its positive or negative for that oscillator value
     * and combines them into one signal
    */
    @GetMapping("/oscillators")
    public ResponseEntity<String> getOscillators(
            @RequestParam BigDecimal rsi,
            @RequestParam BigDecimal stochastic,
            @RequestParam BigDecimal rateOfChange,
            @RequestParam BigDecimal momentum,
            @RequestParam BigDecimal chande
    ){
        String rsiSignal = oscillatorSignalService.getRsiSignal(rsi);
        String stochasticSignal = oscillatorSignalService.getStochasticSignal(stochastic);
        String rocSignal = oscillatorSignalService.getRateOfChangeSignal(rateOfChange);
        String momentumSignal = oscillatorSignalService.getMomentumSignal(momentum);
        String chandeSignal = oscillatorSignalService.getChandeSignal(chande);
        List<String> signalList = List.of(rsiSignal, stochasticSignal, rocSignal, momentumSignal, chandeSignal);
        String combinedSignal = oscillatorSignalService.combineSignals(signalList);

        return new ResponseEntity<>(combinedSignal,HttpStatus.OK);
    }
    /**
     * For each parameter sent as an moving average value, it calls the moving average service
     * to get the signal if its positive or negative for that moving average value
     * and combines them into one signal
    */
    @GetMapping("/movingAverages")
    public ResponseEntity<String> getMovingAverages(
            @RequestParam String symbol,
            @RequestParam BigDecimal sma,
            @RequestParam BigDecimal ema,
            @RequestParam BigDecimal wma,
            @RequestParam BigDecimal tma,
            @RequestParam BigDecimal kama
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        BigDecimal price = prices.get(0);
        String smaSignal = movingAverageSignalService.getSimpleMovingAverageSignal(price,sma);
        String emaSignal = movingAverageSignalService.getExponentialMovingAverageSignal(price,ema);
        String wmaSignal = movingAverageSignalService.getWeightedMovingAverageSignal(price,wma);
        String tmaSignal = movingAverageSignalService.getTriangularMovingAverageSignal(price,tma);
        String kamaSignal = movingAverageSignalService.getKaufmanAdaptiveMovingAverageSignal(price,kama);
        List<String> signalList = List.of(smaSignal, emaSignal, wmaSignal, tmaSignal, kamaSignal);
        String combinedSignal = movingAverageSignalService.combineSignals(signalList);

        return new ResponseEntity<>(combinedSignal,HttpStatus.OK);
    }
}
