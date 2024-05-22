package mate.academy.mybookshop.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.category.CategoryResponseDto;
import mate.academy.mybookshop.dto.category.CreateCategoryRequestDto;
import mate.academy.mybookshop.dto.category.UpdateCategoryRequestDto;
import mate.academy.mybookshop.entity.CategoryEntity;
import mate.academy.mybookshop.mapper.CategoryMapper;
import mate.academy.mybookshop.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a category by id " + id)));
    }

    @Override
    public CategoryResponseDto create(CreateCategoryRequestDto categoryDto) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(categoryEntity));
    }

    @Override
    public CategoryResponseDto update(Long id, UpdateCategoryRequestDto categoryDto) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't update a category by id " + id));
        CategoryEntity updateEntity = categoryMapper.toEntity(categoryDto);
        updateEntity.setId(categoryEntity.getId());
        return categoryMapper.toDto(categoryRepository.save(updateEntity));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
