package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.entity.BookEntity;

public interface BookService {
    BookEntity save(BookEntity book);

    List findAll();
}
