package mk.finki.ukim.dians.stocksapp.service.implementation;

import mk.finki.ukim.dians.stocksapp.service.OscillatorSignalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OscillatorSignalServiceImpl implements OscillatorSignalService {

    @Override
    public String getRsiSignal(BigDecimal rsi) {
        if (rsi.compareTo(BigDecimal.valueOf(70)) >= 0) {
            return "Strong Buy";
        } else if (rsi.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return "Buy";
        } else if (rsi.compareTo(BigDecimal.valueOf(30)) >= 0) {
            return "Neutral";
        } else {
            return "Sell";
        }
    }

    @Override
    public String getStochasticSignal(BigDecimal stochastic) {
        if (stochastic.compareTo(BigDecimal.valueOf(80)) >= 0) {
            return "Strong Buy";
        } else if (stochastic.compareTo(BigDecimal.valueOf(60)) >= 0) {
            return "Buy";
        } else if (stochastic.compareTo(BigDecimal.valueOf(40)) >= 0) {
            return "Neutral";
        } else {
            return "Sell";
        }
    }

    @Override
    public String getRateOfChangeSignal(BigDecimal roc) {
        if (roc.compareTo(BigDecimal.ZERO) > 0) {
            return "Buy";
        } else {
            return "Sell";
        }
    }

    @Override
    public String getMomentumSignal(BigDecimal momentum) {
        if (momentum.compareTo(BigDecimal.ZERO) > 0) {
            return "Buy";
        } else {
            return "Sell";
        }
    }

    @Override
    public String getChandeSignal(BigDecimal chande) {
        if (chande.compareTo(BigDecimal.ZERO) > 0) {
            return "Buy";
        } else {
            return "Sell";
        }
    }

    @Override
    public String combineSignals(List<String> signals) {
        int buyCount = 0;
        int sellCount = 0;

        for (String signal : signals) {
            if (signal.equals("Strong Buy") || signal.equals("Buy")) {
                buyCount++;
            } else if (signal.equals("Strong Sell") || signal.equals("Sell")) {
                sellCount++;
            }
        }

        if (buyCount > sellCount) {
            return "Buy";
        } else if (sellCount > buyCount) {
            return "Sell";
        } else {
            return "Neutral";
        }
    }
}
