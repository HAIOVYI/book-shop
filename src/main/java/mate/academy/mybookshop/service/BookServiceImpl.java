package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }
}
