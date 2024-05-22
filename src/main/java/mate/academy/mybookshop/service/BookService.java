package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.BookDtoWithoutCategoryIds;
import mate.academy.mybookshop.dto.CreateBookRequestDto;
import mate.academy.mybookshop.dto.UpdateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(Pageable pageable);

    BookDto update(Long id, UpdateBookRequestDto bookRequestDto);

    void delete(Long id);

    List<BookDtoWithoutCategoryIds> findAllBooksByCategoryId(Long categoryId, Pageable pageable);
}
