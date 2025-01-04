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
     *  Returns all the stock data, or it filters by the parameters which are not null
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
     *
     * @param symbol the stock for which we are going to calculate
     * @param period the period for which we are going to calculate
     * @param indicator the technical oscillator or moving average we calculate
     * @return the index value for that indicator
     */
    @GetMapping("/indicator")
    public ResponseEntity<BigDecimal> getIndicator(
            @RequestParam String symbol,
            @RequestParam int period,
            @RequestParam String indicator
    ){
        List<StockData> listData = stockService.getByStockSymbol(symbol);
        List<BigDecimal> prices = stockService.getLastTransactionPrices(listData);

        Collections.reverse(prices);
        BigDecimal index = BigDecimal.valueOf(0);
        index = switch (indicator) {
            case "rsi" -> stockAnalysisService.calculateRSI(prices, period);
            case "stochastic" -> stockAnalysisService.calculateStochasticK(prices, period);
            case "roc" -> stockAnalysisService.calculateROC(prices, period);
            case "momentum" -> stockAnalysisService.calculateMomentum(prices, period);
            case "sma" -> stockAnalysisService.calculateSMAOscillator(prices, period);
            case "cmo" -> stockAnalysisService.calculateCMO(prices, period);
            case "ema" -> stockAnalysisService.calculateEMA(prices, period);
            case "wma" -> stockAnalysisService.calculateWMA(prices, period);
            case "tma" -> stockAnalysisService.calculateTMA(prices, period);
            case "kama" -> stockAnalysisService.calculateKAMA(prices, period);
            default -> index;
        };

        return new ResponseEntity<>(index,HttpStatus.OK);
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
