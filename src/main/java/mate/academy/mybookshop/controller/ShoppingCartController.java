package mate.academy.mybookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.ShoppingCartResponseDto;
import mate.academy.mybookshop.dto.cartitem.UpdateCartItemQuantityRequestDto;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.service.ShoppingCartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping carts")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Retrieve user shopping cart")
    public ShoppingCartResponseDto retrieveUserShoppingCart(
            @AuthenticationPrincipal UserEntity user) {
        return shoppingCartService.retrieveUserShoppingCart(user);
    }

    @PostMapping
    @Operation(summary = "Add items to shopping cart",
            description = "Add items to the shopping cart")
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            @RequestBody @Valid CreateCartItemRequestDto requestDto,
            @AuthenticationPrincipal UserEntity user) {
        return shoppingCartService.addCartItemToShoppingCart(requestDto, user);
    }

    @PutMapping("/cart-items/{id}")
    @Operation(summary = "Update cart item quantity",
            description = "Update quantity of a cart item in the shopping cart")
    public ShoppingCartResponseDto updateCartItemQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCartItemQuantityRequestDto requestDto,
            @AuthenticationPrincipal UserEntity user) {
        return shoppingCartService.updateCartItemQuantity(id, requestDto, user);
    }

    @DeleteMapping("/cart-items/{id}")
    @Operation(summary = "Delete cart item",
            description = "Delete cart item from shopping cart by id")
    public ShoppingCartResponseDto deleteCartItem(@PathVariable Long id,
                               @AuthenticationPrincipal UserEntity user) {
        return shoppingCartService.deleteCartItem(id, user);
    }
}
