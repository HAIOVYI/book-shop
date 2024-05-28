package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.order.CreateOrderRequestDto;
import mate.academy.mybookshop.dto.order.OrderResponseDto;
import mate.academy.mybookshop.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderMapper {
    OrderEntity toEntity(CreateOrderRequestDto requestDto);

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(OrderEntity orderEntity);
}
