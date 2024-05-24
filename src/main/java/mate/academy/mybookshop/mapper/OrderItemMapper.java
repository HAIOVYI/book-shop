package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.mybookshop.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = BookMapper.class)
public interface OrderItemMapper {

        @Mapping(source = "book.id", target = "bookId")
        OrderItemResponseDto toDto(OrderItemEntity orderItem);
}
