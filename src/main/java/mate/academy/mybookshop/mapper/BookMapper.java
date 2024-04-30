package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.CreateBookRequestDto;
import mate.academy.mybookshop.dto.UpdateBookRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(BookEntity bookEntity);

    BookEntity toEntity(CreateBookRequestDto bookRequestDto);

    BookEntity toEntity(UpdateBookRequestDto bookRequestDto);
}
