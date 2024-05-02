package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.CreateBookRequestDto;
import mate.academy.mybookshop.dto.UpdateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto findById(Long id);

    List<BookDto> findAll(int page, int size, String sortBy, String sortOrder);

    BookDto update(Long id, UpdateBookRequestDto bookRequestDto);

    void delete(Long id);
}
