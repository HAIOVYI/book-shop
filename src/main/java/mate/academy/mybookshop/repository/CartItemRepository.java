package mate.academy.mybookshop.repository;

import java.util.Set;
import mate.academy.mybookshop.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    Set<CartItemEntity> findCartItemEntitiesByShoppingCartId(Long shoppingCartId);
}
