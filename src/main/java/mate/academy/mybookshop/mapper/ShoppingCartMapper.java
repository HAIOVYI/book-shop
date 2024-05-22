package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.ShoppingCartResponseDto;
import mate.academy.mybookshop.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItemResponseDto")
    ShoppingCartResponseDto toDto(ShoppingCartEntity shoppingCart);

    ShoppingCartEntity toEntity(CreateCartItemRequestDto requestDto);
}
