package com.example.demo.controller;

import com.example.demo.model.ExchangeRate;
import com.example.demo.model.MonthlyLimit;
import com.example.demo.model.Transaction;
import com.example.demo.repository.ExchangeRateRep;
import com.example.demo.repository.MonthlyLimitRep;
import com.example.demo.repository.TransactionRep;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class HomeController {
    private final TransactionRep transactionRep;
    private final MonthlyLimitRep monthlyLimitRep;
    private final ExchangeRateRep exchangeRateRep;

    public HomeController(TransactionRep transactionRep, MonthlyLimitRep monthlyLimitRep, ExchangeRateRep exchangeRateRep) {
        this.transactionRep = transactionRep;
        this.monthlyLimitRep = monthlyLimitRep;
        this.exchangeRateRep = exchangeRateRep;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction saveTransaction = transactionRep.save(transaction);
        return ResponseEntity.ok(saveTransaction);
    }

    @GetMapping(value = "transaction")
    public List<Transaction> getAll() {
        return transactionRep.findAll();
    }

    @GetMapping("/limits")
    public ResponseEntity<MonthlyLimit> getMonthlyLimit(@RequestParam("monthYear") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate monthYear) {
        Optional<MonthlyLimit> optionalLimit = monthlyLimitRep.findById(monthYear);
        if (optionalLimit.isPresent()) {
            return ResponseEntity.ok(optionalLimit.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exchange-rates")
    public ResponseEntity<ExchangeRate> getExchangeRate(@RequestParam("rateDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rateDate) {
        Optional<ExchangeRate> optionalRate = exchangeRateRep.findById(rateDate);
        if (optionalRate.isPresent()) {
            return ResponseEntity.ok(optionalRate.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("getExpenseOperations")
    public ResponseEntity<List<Transaction>> getExpenseOperations(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Получаем список расходных операций за указанный период
        List<Transaction> expenseOperations = transactionRep.findAllByOperationDateBetween(startDate, endDate);

        return ResponseEntity.ok(expenseOperations);
    }

    @PostMapping("createExpenseOperation")
    public ResponseEntity<Transaction> createExpenseOperation(@RequestBody Transaction transaction) {
        // Получаем месячный лимит для указанного месяца
        Optional<MonthlyLimit> optionalLimit = monthlyLimitRep.findById(transactionRep.getOperationTime().withDayOfMonth(1));

        if (optionalLimit.isPresent()) {
            MonthlyLimitRep monthlyLimit = (MonthlyLimitRep) optionalLimit.get();

            // Получаем биржевой курс для указанной даты
            Optional<ExchangeRate> optionalRate = exchangeRateRep.findById(transactionRep.getOperationTime());

            if (optionalRate.isPresent()) {
                ExchangeRateRep exchangeRateRep = (ExchangeRateRep) optionalRate.get();

                // Конвертируем сумму расходной операции в доллары США
                BigDecimal convertedAmount = transactionRep.getAmount().divide(exchangeRateRep.getRate(), 2, RoundingMode.HALF_UP);
                // Получаем лимит для категории расходной операции
                BigDecimal categoryLimit = "goods".equals(transaction.getCategory()) ? monthlyLimit.getGoodsLimit() : monthlyLimit.getServicesLimit();

                // Проверяем, не превышен ли месячный лимит
                if (convertedAmount.compareTo(categoryLimit) > 0) {
                    // Помечаем транзакцию как превысившую месячный лимит
                    transaction.setExceeded(true);
                }
            }
        }

                // Сохраняем расходную операцию в БД
        Transaction savedExpenseOperation = transactionRep.save(transaction);
        return ResponseEntity.ok(savedExpenseOperation);

    }
}
