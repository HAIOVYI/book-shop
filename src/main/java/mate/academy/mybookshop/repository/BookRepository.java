package mate.academy.mybookshop.repository;

import java.util.List;
import mate.academy.mybookshop.entity.BookEntity;

public interface BookRepository {
    BookEntity save(BookEntity book);

    BookEntity getById(Long id);

    List<BookEntity> findAll();
}
