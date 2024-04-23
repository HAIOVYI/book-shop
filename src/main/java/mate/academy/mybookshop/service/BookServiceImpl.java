package mate.academy.mybookshop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.CreateBookRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.mapper.BookMapper;
import mate.academy.mybookshop.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(bookEntity));
    }

    @Override
    public BookDto getById(Long id) {
        return bookMapper.toDto(bookRepository.getById(id));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
