package mk.finki.ukim.dians.stocksapp.model;

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
@NoArgsConstructor
@AllArgsConstructor
public class StockData implements Serializable {
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
}
