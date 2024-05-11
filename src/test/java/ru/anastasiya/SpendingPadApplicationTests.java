package ru.anastasiya;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.anastasiya.category.CategoryService;
import ru.anastasiya.category.CategoryServiceImpl;
import ru.anastasiya.entity.Category;
import ru.anastasiya.entity.Mcc;
import ru.anastasiya.entity.Month;
import ru.anastasiya.entity.Transaction;
import ru.anastasiya.exceptions.CategoryServiceException;
import ru.anastasiya.repository.CategoryRepository;
import ru.anastasiya.repository.MccRepository;
import ru.anastasiya.repository.TransactionRepository;
import ru.anastasiya.transaction.TransactionService;
import ru.anastasiya.transaction.TransactionServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {CategoryRepository.class, TransactionRepository.class, MccRepository.class})
@ContextConfiguration
class SpendingPadApplicationTests {

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private MccRepository mccRepository;

    private CategoryService categoryService;
    private TransactionService transactionService;

    @BeforeEach
    void init(){
        categoryService = new CategoryServiceImpl(categoryRepository, mccRepository);
        transactionService = new TransactionServiceImpl(transactionRepository, categoryRepository);
    }

    @Test
    void findCategoryCaughtException() {
        Mockito.when(categoryRepository.findByName(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(CategoryServiceException.class, () -> categoryService.findOne("food"));
    }

    @Test
    void addExistedMcc(){
        Mockito.when(mccRepository.findByMcc(Mockito.anyInt())).thenReturn(Mockito.mock(Mcc.class));
        Assertions.assertThrows(CategoryServiceException.class,
                () -> categoryService.addMccCode(List.of(1111), "food"));
    }

    @Test
    void addTransaction(){
        var expected = new ArrayList<Transaction>();
        expected.add(new Transaction(123L, Month.OCTOBER, new Category("food"), null));
        Mockito.when(categoryRepository.findByName(Mockito.anyString())).thenReturn(Mockito.mock(Category.class));
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(new Transaction(123L, Month.OCTOBER, new Category("food"), null));
        var result = transactionService.save(123L, Month.OCTOBER, "food", 0);
        Mockito.when(
                transactionRepository.findByCategoryAndValueAndMonth(Mockito.any(Category.class), Mockito.anyLong(), Mockito.any(Month.class)))
                .thenReturn(expected);

        Assertions.assertEquals(result, transactionService.findByCategoryAndValueAndMonth(123L, Month.OCTOBER, "food"));

    }

    @Test
    void findByMonth(){
        var expected = new ArrayList<String>();
        expected.add("supermarket 1p 50%");
        expected.add("food 1p 50%");
        var transactionArrayList = new ArrayList<Transaction>();
        transactionArrayList.add(new Transaction(1L, Month.OCTOBER, new Category("food"), null));
        transactionArrayList.add(new Transaction(0L, Month.OCTOBER, new Category("food"), null));
        transactionArrayList.add(new Transaction(1L, Month.OCTOBER, new Category("supermarket"), null));
        Mockito.when(transactionRepository.findByMonth(Mockito.any(Month.class))).thenReturn(transactionArrayList);
        var result = transactionService.findByMonth(Month.OCTOBER);
        Assertions.assertEquals(expected, result);
    }

}
