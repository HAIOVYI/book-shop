package mate.academy.mybookshop.mapper;

import mate.academy.mybookshop.config.MapperConfig;
import mate.academy.mybookshop.dto.UserRegistrationRequestDto;
import mate.academy.mybookshop.dto.UserResponseDto;
import mate.academy.mybookshop.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toResponseDto(UserEntity userEntity);

    UserEntity toEntity(UserRegistrationRequestDto userRegistrationRequestDto);
}
