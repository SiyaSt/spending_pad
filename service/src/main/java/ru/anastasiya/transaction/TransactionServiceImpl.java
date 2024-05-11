package ru.anastasiya.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anastasiya.dto.MappingUtils;
import ru.anastasiya.dto.TransactionDto;
import ru.anastasiya.entity.Category;
import ru.anastasiya.entity.Mcc;
import ru.anastasiya.entity.Month;
import ru.anastasiya.entity.Transaction;
import ru.anastasiya.exceptions.TransactionServiceException;
import ru.anastasiya.repository.CategoryRepository;
import ru.anastasiya.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public TransactionDto save(Long value, Month month, String name, Integer mccCode) {
        var category = findCategory(name);
        Mcc mcc = null;
        if(mccCode != 0){
            mcc = category
                    .getMccCodes()
                    .stream()
                    .filter(a -> Objects.equals(a.getMcc(), mccCode))
                    .findFirst()
                    .orElseThrow(() -> TransactionServiceException.mccNotFound(mccCode));
        }
        var transaction = transactionRepository.save(new Transaction(value, month, category, mcc));
        return MappingUtils.mapTransactionToTransactionDto(transaction);
    }

    @Override
    @Transactional
    public void delete(Long value, Month month, String category) {
        var transaction = find(value, month, category);
        transactionRepository.delete(transaction.stream().findFirst().get());
    }

    @Override
    public TransactionDto findByCategoryAndValueAndMonth(Long value, Month month, String category) {
        return MappingUtils.mapTransactionToTransactionDto(find(value, month, category).stream().findFirst().get());
    }

    @Override
    public List<TransactionDto> findAll() {
        return transactionRepository.findAll().stream().map(MappingUtils::mapTransactionToTransactionDto).toList();
    }

    @Override
    public List<String> findByCategory(String category) {
        var name = findCategory(category);
        var transactions = transactionRepository.findByCategory(name);
        Map<String, Long> map = new HashMap<>();
        Long sum = 0L;
        for (var transaction : transactions) {
            if(map.containsKey(transaction.getMonth().name())) {
                map.put(transaction.getMonth().name(), map.get(transaction.getMonth().name()) + transaction.getValue());
            }
            else {
                map.put(transaction.getMonth().name(), transaction.getValue());
            }
            sum += transaction.getValue();
        }

        return countPercentage(map, sum);
    }

    @Override
    public List<String> findByMonth(Month month) {
        var transactions = transactionRepository.findByMonth(month);
        Map<String, Long> map = new HashMap<>();
        Long sum = 0L;
        for (var transaction : transactions) {
            if(map.containsKey(transaction.getCategory().getName())) {
                map.put(
                        transaction.getCategory().getName(),
                        map.get(transaction.getCategory().getName()) + transaction.getValue());
            }
            else {
                map.put(transaction.getCategory().getName(), transaction.getValue());
            }
            sum += transaction.getValue();
        }

        return countPercentage(map, sum);
    }

    private Category findCategory(String name){
        var category = categoryRepository.findByName(name);
        if( category == null){
            throw TransactionServiceException.categoryNotFound(name);
        }
        return category;
    }

    private List<Transaction> find(Long value, Month month, String name) {
        var category = findCategory(name);
        var transaction = transactionRepository.findByCategoryAndValueAndMonth(category, value, month);
        if(transaction == null){
            throw TransactionServiceException.categoryNotFound(name);
        }
        return transaction;
    }

    private List<String> countPercentage(Map<String, Long> map, Long sum) {
        var result = new ArrayList<String>();
        for(var value : map.entrySet()) {
            var percentage = (value.getValue()*100/sum);
            result.add(String.format("%s %dp %d%%", value.getKey(),value.getValue(), percentage));
        }
        return result;
    }
}
