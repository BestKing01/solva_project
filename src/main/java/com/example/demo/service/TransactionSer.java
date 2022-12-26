package com.example.demo.service;

import com.example.demo.component.ExchangeRateApiClient;
import com.example.demo.model.ExchangeRate;
import com.example.demo.model.ExchangeRateResponse;
import com.example.demo.model.MonthlyLimit;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ExchangeRateRep;
import com.example.demo.repository.MonthlyLimitRep;
import com.example.demo.repository.TransactionRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionSer {
    @Autowired
    private TransactionRep transactionRepository;

    @Autowired
    private MonthlyLimitRep monthlyLimitRepository;

    @Autowired
    private ExchangeRateRep exchangeRateRepository;

    @Autowired
    private TransactionSer transactionSer;
    @Autowired
    private ExchangeRateApiClient exchangeRateApiClient;

    public void updateExchangeRates() {
        // Make a request to the exchange rate API
        ExchangeRateResponse response = exchangeRateApiClient.getExchangeRates("KZT", "USD", "1day");

        // Save the exchange rates to the database
        for (ExchangeRate rate : response.getRates()) {
            exchangeRateRepository.save(rate);
        }
    }

    public BigDecimal getMonthlyLimit(String currency) {
        // Validate the currency
        if (!currency.equals("USD")) {
            throw new IllegalArgumentException("Invalid currency. Only USD is supported.");
        }

        // Retrieve the monthly limit from the database
        Optional<MonthlyLimit> limit = monthlyLimitRepository.findByCurrency(currency);
        return limit.map(MonthlyLimit::getLimit).orElse(BigDecimal.ZERO);
    }
    public void setMonthlyLimit(BigDecimal limit, String currency) {
        // Validate the currency
        if (!currency.equals("USD")) {
            throw new IllegalArgumentException("Invalid currency. Only USD is supported.");
        }

        // Set the monthly limit
        monthlyLimitRepository.save(new MonthlyLimit(currency, limit, LocalDate.now()));
    }
        public Transaction saveTransaction(Transaction transaction) {
        // Calculate the exchange rate if necessary
        if (!transaction.getCurrency().equals("USD")) {
            // Get the exchange rate for the specified currency
            BigDecimal rate = exchangeRateRepository.findByDate(LocalDate.now())
                    .map(ExchangeRate::getRate)
                    .orElseGet(() -> exchangeRateRepository.findTopByOrderByDateDesc()
                            .map(ExchangeRate::getRate)
                            .orElse(BigDecimal.ZERO));

            // Convert the transaction amount to USD
            transaction.setAmount(transaction.getAmount().multiply(rate));
            transaction.setCurrency(Currency.getInstance("USD"));
        }

        // Check if the transaction exceeds the monthly limit
        BigDecimal limit = monthlyLimitRepository.findByCurrency("USD")
                .map(MonthlyLimit::getLimit)
                .orElse(BigDecimal.ZERO);
        if (transaction.getAmount().compareTo(limit) > 0) {
            transaction.setLimitExceeded(true);
        }
        // Save the transaction to the database
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionsExceedingLimit(String currency) {
        return transactionRepository.findTransactionsExceedingLimit();
    }
}