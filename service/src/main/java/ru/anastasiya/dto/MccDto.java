package ru.anastasiya.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MccDto {
    Long id;
    Integer mcc;
    Long category_id;
}
