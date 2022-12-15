package com.example.demo;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	private TransactionRep transactionRep;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Transactional
	public void markExceededTransactions() {
		// Получаем список всех транзакций, совершенных в текущем месяце
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		List<Transaction> transactions = transactionRep.findByLocalDate(year, month);

		// Считаем количество операций для каждой транзакции
		for (Transaction transaction : transactions) {
			int count = transactionRep.countByTransactionId(transaction.getId());
			// Если количество операций превысило месячный лимит, устанавливаем флаг limit_exceeded
			if (count > transaction.getMonthlyLimit()) {
				transaction.setLimitExceeded(true);
				transactionRepository.save(transaction);
			}
		}
	}

}
