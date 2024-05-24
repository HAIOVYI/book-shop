package mate.academy.mybookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import mate.academy.mybookshop.dto.orderitem.OrderItemResponseDto;

public record OrderResponseDto(Long id,
                               Long userId,
                               Set<OrderItemResponseDto> orderItems,
                               LocalDateTime orderDate,
                               BigDecimal total,
                               String status) {
}
