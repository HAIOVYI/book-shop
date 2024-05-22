package mate.academy.mybookshop.dto.cartitem;

import java.util.Set;

public record ShoppingCartResponseDto(Long id,
                                      Long userId,
                                      Set<CartItemResponseDto> cartItemResponseDto) {
}
