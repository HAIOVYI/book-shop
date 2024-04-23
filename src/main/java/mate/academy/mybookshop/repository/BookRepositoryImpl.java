package mate.academy.mybookshop.repository;

import java.util.List;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BookEntity save(BookEntity book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert book into DB: " + book, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public BookEntity getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(BookEntity.class, id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get book by id: " + id, e);
        }
    }

    @Override
    public List<BookEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<BookEntity> getAllBooks
                    = session.createQuery("from BookEntity", BookEntity.class);
            return getAllBooks.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all books", e);
        }
    }
}
