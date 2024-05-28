package mate.academy.mybookshop.repository;

import java.util.Set;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.entity.CartItemEntity;
import mate.academy.mybookshop.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Set<CartItemEntity> findCartItemEntitiesByShoppingCartId(Long shoppingCartId);

    void deleteCartItemEntitiesById(Long shoppingCartId);

    CartItemEntity findByShoppingCartAndBook(ShoppingCartEntity shoppingCart, BookEntity book);
}
