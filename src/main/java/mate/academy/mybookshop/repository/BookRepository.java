package mate.academy.mybookshop.repository;

import mate.academy.mybookshop.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
