package ru.anastasiya.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anastasiya.dto.CategoryDto;
import ru.anastasiya.dto.MappingUtils;
import ru.anastasiya.dto.MccDto;
import ru.anastasiya.entity.Category;
import ru.anastasiya.entity.Mcc;
import ru.anastasiya.exceptions.CategoryServiceException;
import ru.anastasiya.repository.CategoryRepository;
import ru.anastasiya.repository.MccRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MccRepository mccRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, MccRepository mccRepository) {
        this.categoryRepository = categoryRepository;
        this.mccRepository = mccRepository;
    }

    @Override
    @Transactional
    public void save(String name, List<Integer> mccCodes) {
        if (categoryRepository.findByName(name) != null) {
            throw CategoryServiceException.CategoryExist(name);
        }
        var category = categoryRepository.save(new Category(name));
        for (Integer mccCode : mccCodes) {
            if (mccRepository.findByMcc(mccCode) != null) {
                throw CategoryServiceException.MccExist();
            }
            category.addMcc(new Mcc(mccCode, category));
        }
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(MappingUtils::mapCategoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto findOne(String name) {
        return MappingUtils.mapCategoryToCategoryDto(find(name));
    }

    private Category find(String name) {
        var category = categoryRepository.findByName(name);
        if (category == null) {
            throw CategoryServiceException.CategoryNotExist(name);
        }
        return category;
    }

    @Override
    @Transactional
    public void delete(String name) {
        var category = find(name);
        var subcategories = category.getSubcategories();
        for (var subcategory : subcategories) {
            var list = subcategory.getSubcategories();
            list.remove(category);
            categoryRepository.save(subcategory);
        }
        category.getSubcategories().clear();
        categoryRepository.save(category);
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getCategories(String name) {
        var category = find(name);
        return category.getSubcategories().stream().map(MappingUtils::mapCategoryToCategoryDto).toList();
    }

    @Override
    @Transactional
    public void addMccCode(List<Integer> mccCodes, String name) {
        var category = find(name);
        for (Integer mccCode : mccCodes) {
            if (mccRepository.findByMcc(mccCode) != null) {
                throw CategoryServiceException.MccExist();
            }
            category.addMcc(new Mcc(mccCode, category));
        }
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void addGroup(String name, List<String> groups) {
        var category = find(name);
        for (var group : groups) {
            var subcategory = find(group);
            category.addSubcategory(subcategory);
            subcategory.addSubcategory(category);
            categoryRepository.save(subcategory);
        }
        categoryRepository.save(category);
    }

    @Override
    public List<MccDto> getMccCodes(String name) {
        return find(name).getMccCodes().stream().map(MappingUtils::mapMccToMccDto).toList();
    }

}
