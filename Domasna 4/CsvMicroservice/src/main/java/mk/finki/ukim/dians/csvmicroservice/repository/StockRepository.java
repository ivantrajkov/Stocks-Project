package mk.finki.ukim.dians.csvmicroservice.repository;


import mk.finki.ukim.dians.csvmicroservice.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockData, Long> {

    @Query("SELECT s FROM StockData s WHERE " +
            "( :symbol IS NULL OR LOWER(s.stockSymbol) = LOWER(:symbol)) AND " +
            "( :avgPrice IS NULL OR s.averagePrice > :avgPrice) AND " +
            "( :fromDate IS NULL OR s.date >= :fromDate) AND " +
            "( :toDate IS NULL OR s.date <= :toDate) ")
    List<StockData> getFilteredStockData(
            @Param("symbol") String symbol,
            @Param("avgPrice") BigDecimal avgPrice,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate
    );

    @Query("SELECT s FROM StockData s WHERE (:symbol IS NULL OR LOWER(s.stockSymbol) = LOWER(:symbol))")
    List<StockData> getByStockSymbol(
            @Param("symbol") String symbol
    );
    @Query("SELECT DISTINCT s.stockSymbol FROM StockData s")
    List<String> findDistinctStockSymbols();

}