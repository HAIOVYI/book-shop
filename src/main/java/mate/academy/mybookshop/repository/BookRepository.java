package mate.academy.mybookshop.repository;

import java.util.List;
import mate.academy.mybookshop.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findAllByCategoriesId(Long categoryId);
}
