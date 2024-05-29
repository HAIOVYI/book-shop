package mate.academy.mybookshop.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.order.CreateOrderRequestDto;
import mate.academy.mybookshop.dto.order.OrderResponseDto;
import mate.academy.mybookshop.dto.order.UpdateOrderRequestDto;
import mate.academy.mybookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.mybookshop.entity.CartItemEntity;
import mate.academy.mybookshop.entity.OrderEntity;
import mate.academy.mybookshop.entity.OrderItemEntity;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.exception.EmptyCartException;
import mate.academy.mybookshop.exception.EntityNotFoundException;
import mate.academy.mybookshop.mapper.OrderItemMapper;
import mate.academy.mybookshop.mapper.OrderMapper;
import mate.academy.mybookshop.repository.CartItemRepository;
import mate.academy.mybookshop.repository.OrderItemRepository;
import mate.academy.mybookshop.repository.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto, UserEntity user) {
        Set<CartItemEntity> cartItems = cartItemRepository
                .findCartItemEntitiesByShoppingCartId(user.getId());
        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Cannot create order without items.");
        }

        OrderEntity order = orderMapper.toEntity(requestDto);
        order.setUser(user);
        order.setTotal(calculateTotal(cartItems));
        order.setOrderItems(mapCartItemsToOrderItems(order, cartItems));

        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(UserEntity user, Pageable pageable) {
        return orderRepository.getAllByUserId(user.getId(), pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(
            UserEntity user, Long orderId, Pageable pageable) {
        return orderItemRepository.findByOrderIdAndUserId(user.getId(), orderId, pageable).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(UserEntity user, Long orderId, Long orderItemId) {
        return orderItemRepository.findByOrderIdAndItemIdAndUserId(
                        user.getId(), orderId, orderItemId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Order item "
                        + orderItemId + " not found in the order "
                        + orderId + " for user " + user.getId()));
    }

    @Override
    public void changeStatus(Long orderId, UpdateOrderRequestDto requestDto) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Order with id: " + orderId + " does not exist"));
        order.setStatus(requestDto.status());
        orderRepository.save(order);
    }

    private BigDecimal calculateTotal(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItemEntity> mapCartItemsToOrderItems(
            OrderEntity order, Set<CartItemEntity> cartItems) {
        Set<OrderItemEntity> orderItems = new HashSet<>();

        for (CartItemEntity cartItem : cartItems) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice().multiply(
                    new BigDecimal(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
