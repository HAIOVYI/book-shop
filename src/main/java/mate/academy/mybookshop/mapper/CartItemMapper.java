package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.cartitem.CartItemResponseDto;
import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.UpdateCartItemQuantityRequestDto;
import mate.academy.mybookshop.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItemEntity cartItemEntity);

    @Mapping(source = "bookId", target = "book.id")
    @Mapping(source = "quantity", target = "quantity")
    CartItemEntity toEntity(CreateCartItemRequestDto createCartItemRequestDto);

    @Mapping(source = "quantity", target = "quantity")
    CartItemEntity toEntity(UpdateCartItemQuantityRequestDto requestDto);
}
