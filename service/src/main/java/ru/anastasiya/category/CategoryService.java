package ru.anastasiya.category;

import ru.anastasiya.dto.CategoryDto;
import ru.anastasiya.dto.MccDto;

import java.util.List;

public interface CategoryService {
    void save(String name, List<Integer> mccCodes);
    List<CategoryDto> findAll();
    CategoryDto findOne(String name);
    void delete(String name);
    List<CategoryDto> getCategories(String name);
    void addMccCode(List<Integer> mccCode, String name);
    void addGroup(String name, List<String> groups);
    List<MccDto> getMccCodes(String name);
}
