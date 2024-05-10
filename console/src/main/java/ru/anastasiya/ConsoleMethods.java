package ru.anastasiya;

import org.springframework.stereotype.Component;
import picocli.CommandLine;
import ru.anastasiya.category.CategoryService;
import ru.anastasiya.entity.Month;
import ru.anastasiya.transaction.TransactionService;

import java.util.List;

@Component
@CommandLine.Command(name = "ConsoleMethods", description = "contains console methods")
public class ConsoleMethods {
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    public ConsoleMethods(CategoryService categoryService, TransactionService transactionService) {
        this.categoryService = categoryService;
        this.transactionService = transactionService;
    }


    @CommandLine.Command(name = "add_category", description = "save new category")
    public void addCategory(@CommandLine.Parameters(index = "0", description = "Category name") String name,
                            @CommandLine.Parameters(arity = "1..*", description = "mcc") List<Integer> mccCodes) {

        categoryService.save(name, mccCodes);
        CommandUtils.writeMessageBlue("Created new category with name " + name + " and mcc " + mccCodes);
    }

    @CommandLine.Command(name = "add_mcc_to_category", description = "add new mcc to category")
    public void addMcc(@CommandLine.Parameters(index = "0", description = "Category name") String name,
                       @CommandLine.Parameters(arity = "1..*", description = "mcc") List<Integer> mccCodes) {
        categoryService.addMccCode(mccCodes, name);
        CommandUtils.writeMessageBlue("Added mcc to category" + name + "codes:" + mccCodes);
    }

    @CommandLine.Command(name = "add_group_to_category", description = "add new group to category")
    public void addGroup(@CommandLine.Parameters(index = "0", description = "Category name") String name,
                         @CommandLine.Parameters(arity = "1..*", description = "groups") List<String> groups) {
        categoryService.addGroup(name, groups);
        CommandUtils.writeMessageBlue("Added group to category" + name + "groups:" + groups);
    }

    @CommandLine.Command(name = "remove_category", description = "remove category from list")
    public void removeCategory(@CommandLine.Parameters(index = "0", description = "Category name") String name) {
        categoryService.delete(name);
        CommandUtils.writeMessageBlue("Category with name " + name + " removed from" + categoryService.findAll());
    }

    @CommandLine.Command(name = "add_transaction", description = "add new transaction")
    public void addTransaction(@CommandLine.Parameters(index = "0", description = "category name") String name,
                               @CommandLine.Parameters(index = "1", description = "spent money") Long spentMoney,
                               @CommandLine.Parameters(index = "2", description = "month when spent money") Month month,
                               @CommandLine.Parameters(index = "3", description = "mcc", defaultValue = "0") Integer mcc) {
        transactionService.save(spentMoney, month, name);
        CommandUtils.writeMessageBlue("transaction added into category name: " + name);
    }

    @CommandLine.Command(name = "remove_transaction", description = "remove transaction from list")
    public void removeTransaction(@CommandLine.Parameters(index = "0", description = "category name") String name,
                                  @CommandLine.Parameters(index = "1", description = "spent money") Long spentMoney,
                                  @CommandLine.Parameters(index = "2", description = "month when spent money") Month month) {
        transactionService.delete(spentMoney, month, name);
        CommandUtils.writeMessageBlue("transaction removed from category name: " + name);
    }

    @CommandLine.Command(name = "show_categories", description = "show all categories")
    public void showCategories() {
        categoryService.findAll().forEach(category -> CommandUtils.writeMessageBlue("Category: " + category.getName()));
    }

    @CommandLine.Command(name = "show_by_category", description = "show months and spent money selected by specific category")
    public void showByCategory(@CommandLine.Parameters(index = "0", description = "category name") String name) {
        var list = transactionService.findByCategory(name);
        list.forEach(CommandUtils::writeMessageBlue);
    }

    @CommandLine.Command(name = "show_by_month", description = "show categories and spent money selected by specific month")
    public void showByMonth(@CommandLine.Parameters(index = "0", description = "month") Month month) {
        var list = transactionService.findByMonth(month);
        list.forEach(CommandUtils::writeMessageBlue);
    }
}
