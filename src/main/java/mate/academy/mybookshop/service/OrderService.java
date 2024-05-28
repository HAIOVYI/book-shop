package mate.academy.mybookshop.service;

import java.util.List;
import mate.academy.mybookshop.dto.order.CreateOrderRequestDto;
import mate.academy.mybookshop.dto.order.OrderResponseDto;
import mate.academy.mybookshop.dto.order.UpdateOrderRequestDto;
import mate.academy.mybookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto requestDto, UserEntity user);

    List<OrderResponseDto> getAllOrders(UserEntity user, Pageable pageable);

    List<OrderItemResponseDto> getOrderItems(UserEntity user, Long orderId, Pageable pageable);

    OrderItemResponseDto getOrderItem(UserEntity user, Long orderId, Long id);

    void changeStatus(Long id, UpdateOrderRequestDto requestDto);
}
