package vla.sai.spring.authservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.authservice.dto.PersonDto;
import vla.sai.spring.authservice.dto.UserDto;
import vla.sai.spring.authservice.entity.Person;
import vla.sai.spring.authservice.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {
    @Mapping(source = "userId", target = "user.id")
    Person toEntity(PersonDto personDto);

    @Mapping(source = "user.id", target = "userId")
    PersonDto toDto(Person person);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Person partialUpdate(PersonDto personDto, @MappingTarget Person person);
}