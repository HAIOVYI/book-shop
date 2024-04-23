package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    BookDto getById(Long id);

    List<BookDto> findAll();
}
