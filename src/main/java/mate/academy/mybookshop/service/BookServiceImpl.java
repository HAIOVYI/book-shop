package mate.academy.mybookshop.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.book.BookDto;
import mate.academy.mybookshop.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.mybookshop.dto.book.CreateBookRequestDto;
import mate.academy.mybookshop.dto.book.UpdateBookRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.entity.CategoryEntity;
import mate.academy.mybookshop.exception.EntityNotFoundException;
import mate.academy.mybookshop.mapper.BookMapper;
import mate.academy.mybookshop.repository.BookRepository;
import mate.academy.mybookshop.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        BookEntity bookEntity = bookMapper.toEntity(bookRequestDto);
        setCategories(bookEntity, bookRequestDto.getCategoryIds());
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
    public List<BookDto> findAll(Pageable pageable) {
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
        setCategories(updatedBook, bookRequestDto.getCategoryIds());
        return bookMapper.toDto(bookRepository.save(updatedBook));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllBooksByCategoryId(
            Long categoryId, Pageable pageable) {
        List<BookEntity> bookEntities = bookRepository.findAllByCategoriesId(categoryId, pageable);
        return bookEntities.stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    private void setCategories(BookEntity bookEntity, Set<Long> categoryIds) {
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<CategoryEntity> categories =
                    new HashSet<>(categoryRepository.findAllById(categoryIds));
            Set<Long> foundCategoryIds = categories.stream()
                    .map(CategoryEntity::getId)
                    .collect(Collectors.toSet());
            categoryIds.forEach(categoryId -> {
                if (!foundCategoryIds.contains(categoryId)) {
                    throw new EntityNotFoundException(
                            "Category with id: " + categoryId + " not found");
                }
            });
            bookEntity.setCategories(categories);
        }
    }
}
