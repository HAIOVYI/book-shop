package mate.academy.mybookshop.repository;

import java.util.List;
import mate.academy.mybookshop.entity.OrderItemEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    @Query("SELECT i FROM OrderItemEntity i WHERE i.order.id IN :orderId")
    List<OrderItemEntity> findOrderItemEntitiesByOrderId(Long orderId, Pageable pageable);
}
