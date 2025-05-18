package vla.sai.spring.authservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.authservice.dto.UserImageDataDto;
import vla.sai.spring.authservice.entity.UserImageData;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserImageDataMapper {

    @Mapping(source ="userId", target = "user.id")
    UserImageData toEntity(UserImageDataDto userImageDataDto);

    @Mapping(source = "user.id", target = "userId")
    UserImageDataDto toDto(UserImageData userImageData);

}