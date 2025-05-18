package vla.sai.spring.authservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "userImageData.id", target = "userImageDataId")
    UserDto toDto(User user);

    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "userImageDataId", target = "userImageData.id")
    @Mapping(source = "personId", target = "person.id")
    User toEntity(UserDto userDto);

}