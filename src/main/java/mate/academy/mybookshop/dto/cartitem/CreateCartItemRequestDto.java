package mate.academy.mybookshop.dto.cartitem;

import jakarta.validation.constraints.Min;

public record CreateCartItemRequestDto(Long bookId,
                                       @Min(value = 1, message = "Quantity must be at least 1")
                                       int quantity) {
}
