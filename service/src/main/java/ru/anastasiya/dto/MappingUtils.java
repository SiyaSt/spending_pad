package ru.anastasiya.dto;

import ru.anastasiya.entity.Category;
import ru.anastasiya.entity.Mcc;
import ru.anastasiya.entity.Transaction;

public class MappingUtils {
    public static CategoryDto mapCategoryToCategoryDto(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapCategoryDtoToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getName());
    }

    public static TransactionDto mapTransactionToTransactionDto(Transaction transaction) {
        return TransactionDto
                .builder()
                .id(transaction.getId())
                .value(transaction.getValue())
                .month(transaction.getMonth())
                .category_id(transaction.getCategory().getId())
                .build();
    }

    public static MccDto mapMccToMccDto(Mcc mcc) {
        return MccDto
                .builder()
                .mcc(mcc.getMcc())
                .category_id(mcc.getCategory().getId())
                .build();
    }
}
