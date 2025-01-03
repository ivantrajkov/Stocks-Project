package mk.finki.ukim.dians.stocksapp.service;

import java.math.BigDecimal;
import java.util.List;

public interface OscillatorService {
    String getRsiSignal(BigDecimal rsi);
    String getStochasticSignal(BigDecimal stochastic);
    String getRateOfChangeSignal(BigDecimal roc);
    String getMomentumSignal(BigDecimal momentum);
    String getChandeSignal(BigDecimal chande);
    String combineSignals(List<String> signals);
}
