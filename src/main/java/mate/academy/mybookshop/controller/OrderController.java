package mate.academy.mybookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.order.CreateOrderRequestDto;
import mate.academy.mybookshop.dto.order.OrderResponseDto;
import mate.academy.mybookshop.dto.order.UpdateOrderRequestDto;
import mate.academy.mybookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create order",
            description = "Places order, moves books from cart to order")
    public OrderResponseDto createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto,
                                        @AuthenticationPrincipal UserEntity user) {
        return orderService.createOrder(requestDto, user);
    }

    @GetMapping
    @Operation(summary = "Get orders", description = "Get all user orders")
    public List<OrderResponseDto> getAllOrders(
            @AuthenticationPrincipal UserEntity user, Pageable pageable) {
        return orderService.getAllOrders(user, pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Get items by order id", description = "Get list of items by order id")
    public List<OrderItemResponseDto> getOrderItems(@AuthenticationPrincipal UserEntity user,
                                                    @PathVariable Long orderId,
                                                    Pageable pageable) {
        return orderService.getOrderItems(user, orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "Get item from order",
            description = "Get specific item from specific order by id")
    public OrderItemResponseDto orderItem(@AuthenticationPrincipal UserEntity user,
                                          @PathVariable Long orderId,
                                          @PathVariable Long id) {
        return orderService.getOrderItem(user, orderId, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Change status", description = "ADMIN can change order status by order id")
    public void changeStatus(@PathVariable Long id,
                             @Valid @RequestBody UpdateOrderRequestDto requestDto) {
        orderService.changeStatus(id, requestDto);
    }
}
