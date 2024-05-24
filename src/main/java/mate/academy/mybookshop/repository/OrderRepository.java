package mate.academy.mybookshop.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.mybookshop.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> getAllByUserId(Long id, Pageable pageable);


    Optional<OrderEntity> findByIdAndUserId(Long orderId, Long id);
}
