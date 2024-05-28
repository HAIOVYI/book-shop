package mate.academy.mybookshop.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.mybookshop.entity.OrderItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Query("SELECT i FROM OrderItemEntity i "
            + "WHERE i.order.user.id = :userId "
            + "AND i.order.id = :orderId "
            + "AND i.id = :itemId")
    Optional<OrderItemEntity> findByOrderIdAndItemIdAndUserId(
            Long userId, Long orderId, Long itemId);

    @Query("SELECT i FROM OrderItemEntity i "
            + "WHERE i.order.user.id = :userId "
            + "AND i.order.id = :orderId ")
    List<OrderItemEntity> findByOrderIdAndUserId(Long userId, Long orderId, Pageable pageable);
}
