package ru.anastasiya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anastasiya.entity.Category;
import ru.anastasiya.entity.Month;
import ru.anastasiya.entity.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategoryAndValueAndMonth(Category category, Long value, Month month);
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByMonth(Month month);
}
