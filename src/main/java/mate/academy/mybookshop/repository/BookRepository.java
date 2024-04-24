package mate.academy.mybookshop.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.mybookshop.entity.BookEntity;

public interface BookRepository {
    BookEntity save(BookEntity book);

    Optional<BookEntity> findById(Long id);

    List<BookEntity> findAll();
}
