package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.dto.CategoryResponseDto;
import mate.academy.mybookshop.dto.CreateCategoryRequestDto;
import mate.academy.mybookshop.dto.UpdateCategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto create(CreateCategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, UpdateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
