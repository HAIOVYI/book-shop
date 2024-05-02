package mate.academy.mybookshop.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.BookDto;
import mate.academy.mybookshop.dto.CreateBookRequestDto;
import mate.academy.mybookshop.dto.UpdateBookRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.exception.EntityNotFoundException;
import mate.academy.mybookshop.mapper.BookMapper;
import mate.academy.mybookshop.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public BookDto findById(Long id) {
        return bookMapper.toDto(
                bookRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Can't get book by id: " + id))
        );
    }

    @Override
    public List<BookDto> findAll(int page, int size, String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto bookRequestDto) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't update book by id: " + id));

        var updatedBook = bookMapper.toEntity(bookRequestDto);
        updatedBook.setId(id);
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
