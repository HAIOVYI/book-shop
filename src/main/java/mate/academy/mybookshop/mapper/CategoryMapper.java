package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.CategoryResponseDto;
import mate.academy.mybookshop.dto.CreateCategoryRequestDto;
import mate.academy.mybookshop.dto.UpdateCategoryRequestDto;
import mate.academy.mybookshop.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(CategoryEntity category);

    CategoryEntity toEntity(CreateCategoryRequestDto categoryDto);

    CategoryEntity toEntity(UpdateCategoryRequestDto categoryDto);
}
