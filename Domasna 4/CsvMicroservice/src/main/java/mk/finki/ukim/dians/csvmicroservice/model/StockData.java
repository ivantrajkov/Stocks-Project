package mk.finki.ukim.dians.csvmicroservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "stock_table")
public class StockData implements Serializable {
    public StockData(Long id, String date, BigDecimal lastTransactionPrice, BigDecimal maxPrice, BigDecimal minPrice, BigDecimal averagePrice, BigDecimal percentageChange, BigDecimal quantity, BigDecimal bestTurnover, BigDecimal totalTurnover, String stockSymbol) {
        this.id = id;
        this.date = date;
        this.lastTransactionPrice = lastTransactionPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.averagePrice = averagePrice;
        this.percentageChange = percentageChange;
        this.quantity = quantity;
        this.bestTurnover = bestTurnover;
        this.totalTurnover = totalTurnover;
        this.stockSymbol = stockSymbol;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "last_transaction_price")
    private BigDecimal lastTransactionPrice;

    @Column(name = "max_price")
    private BigDecimal maxPrice;

    @Column(name = "min_price")
    private BigDecimal minPrice;

    @Column(name = "avg_price")
    private BigDecimal averagePrice;

    @Column(name = "percent_change")
    private BigDecimal percentageChange;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "best_turnover")
    private BigDecimal bestTurnover;

    @Column(name = "total_turnover")
    private BigDecimal totalTurnover;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    public StockData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getLastTransactionPrice() {
        return lastTransactionPrice;
    }

    public void setLastTransactionPrice(BigDecimal lastTransactionPrice) {
        this.lastTransactionPrice = lastTransactionPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(BigDecimal percentageChange) {
        this.percentageChange = percentageChange;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBestTurnover() {
        return bestTurnover;
    }

    public void setBestTurnover(BigDecimal bestTurnover) {
        this.bestTurnover = bestTurnover;
    }

    public BigDecimal getTotalTurnover() {
        return totalTurnover;
    }

    public void setTotalTurnover(BigDecimal totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
}
