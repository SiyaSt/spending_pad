package ru.anastasiya.transaction;

import ru.anastasiya.dto.TransactionDto;
import ru.anastasiya.entity.Month;

import java.util.List;

public interface TransactionService {
    TransactionDto save(Long value, Month month, String category, Integer mcc);
    void delete(Long value, Month month, String category);
    TransactionDto findByCategoryAndValueAndMonth(Long value, Month month, String category);
    List<TransactionDto> findAll();
    List<String> findByCategory(String category);
    List<String> findByMonth(Month month);
}
