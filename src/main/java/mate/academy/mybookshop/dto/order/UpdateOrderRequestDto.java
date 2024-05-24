package mate.academy.mybookshop.dto.order;

import jakarta.validation.constraints.NotNull;
import mate.academy.mybookshop.entity.OrderEntity;

public record UpdateOrderRequestDto(@NotNull OrderEntity.Status status) {
}
