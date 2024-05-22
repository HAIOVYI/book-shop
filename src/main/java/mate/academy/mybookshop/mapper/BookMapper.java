package mate.academy.mybookshop.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.book.BookDto;
import mate.academy.mybookshop.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.mybookshop.dto.book.CreateBookRequestDto;
import mate.academy.mybookshop.dto.book.UpdateBookRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.entity.CategoryEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(BookEntity bookEntity);

    BookEntity toEntity(CreateBookRequestDto bookRequestDto);

    BookEntity toEntity(UpdateBookRequestDto bookRequestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(BookEntity bookEntity);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, BookEntity bookEntity) {
        if (bookEntity.getCategories() != null) {
            Set<Long> categoryIds = bookEntity.getCategories().stream()
                    .map(CategoryEntity::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoryIds(categoryIds);
        }
    }

    @Named("bookFromId")
    default BookEntity bookFromId(Long id) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(id);
        return bookEntity;
    }
}
