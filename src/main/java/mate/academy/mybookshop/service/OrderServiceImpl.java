package mate.academy.mybookshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import mate.academy.mybookshop.repository.ShoppingCartRepository;
import mate.academy.mybookshop.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto, UserEntity user) {
        Set<CartItemEntity> cartItems = cartItemRepository.findCartItemEntitiesByShoppingCartId(user.getId());
        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Cannot create order without items.");
        }

        OrderEntity order = orderMapper.toEntity(requestDto);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);

        order.setTotal(calculateTotal(cartItems));
        order.setShippingAddress(orderMapper.toEntity(requestDto).getShippingAddress());

        Set<OrderItemEntity> orderItems = new HashSet<>();
        for (CartItemEntity cartItem : cartItems) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            cartItemRepository.deleteCartItemEntitiesById(cartItem.getId());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(UserEntity user, Pageable pageable) {
        List<OrderEntity> orders = orderRepository.getAllByUserId(user.getId(), pageable);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(UserEntity user, Long orderId, Pageable pageable) {
        OrderEntity order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for the given user."));

        List<OrderItemEntity> orderItems = orderItemRepository.findOrderItemEntitiesByOrderId(orderId, pageable);
        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(UserEntity user, Long orderId, Long id) {
        OrderEntity order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found for the given user."));

        // Знаходимо конкретний елемент замовлення за його id
        OrderItemEntity orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found."));

        // Перевіряємо, чи цей елемент замовлення належить до вказаного замовлення
        if (!orderItem.getOrder().getId().equals(order.getId())) {
            throw new EntityNotFoundException("Order item not found in the specified order.");
        }

        // Перетворюємо OrderItemEntity в OrderItemResponseDto і повертаємо його
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public void changeStatus(UserEntity user, Long id, UpdateOrderRequestDto requestDto) {

    }

    private BigDecimal calculateTotal(Set<CartItemEntity> cartItems) {
        return cartItems.stream()
                .map(item -> item.getBook().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
