package mk.finki.ukim.dians.csvmicroservice.service;

import mk.finki.ukim.dians.csvmicroservice.model.StockData;

import java.util.List;

public interface CSVService {
    /**
     * Generates a CSV file from a list of StockData.
     * This method converts a list of `StockData` objects into a CSV format with the appropriate columns.
     *
     * @param stockData The list of `StockData` objects to be converted into a CSV file.
     * @return A byte array representing the CSV file.
     */
    public byte[] generateCSV(List<StockData> stockData);

}
