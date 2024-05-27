package mate.academy.mybookshop.service;

import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.ShoppingCartResponseDto;
import mate.academy.mybookshop.dto.cartitem.UpdateCartItemQuantityRequestDto;
import mate.academy.mybookshop.entity.UserEntity;

public interface ShoppingCartService {
    ShoppingCartResponseDto retrieveUserShoppingCart(UserEntity user);

    ShoppingCartResponseDto addCartItemToShoppingCart(
            CreateCartItemRequestDto requestDto, UserEntity user);

    ShoppingCartResponseDto updateCartItemQuantity(
            Long id,
            UpdateCartItemQuantityRequestDto requestDto,
            UserEntity user);

    ShoppingCartResponseDto deleteCartItem(Long id, UserEntity user);

    void createShoppingCart(UserEntity user);
}
