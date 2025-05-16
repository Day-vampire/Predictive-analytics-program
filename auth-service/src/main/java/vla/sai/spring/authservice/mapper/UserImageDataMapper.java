package vla.sai.spring.authservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.authservice.dto.UserImageDataDto;
import vla.sai.spring.authservice.entity.UserImageData;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserImageDataMapper {

    UserImageData toEntity(UserImageDataDto userImageDataDto);
    UserImageDataDto toDto(UserImageData userImageData);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserImageData partialUpdate(UserImageDataDto userImageDataDto, @MappingTarget UserImageData userImageData);
}