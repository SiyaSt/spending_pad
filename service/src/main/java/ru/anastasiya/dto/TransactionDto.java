package ru.anastasiya.dto;

import lombok.Builder;
import lombok.Data;
import ru.anastasiya.entity.Month;

@Data
@Builder
public class TransactionDto {
    Long id;
    Long value;
    Month month;
    Long category_id;
}
