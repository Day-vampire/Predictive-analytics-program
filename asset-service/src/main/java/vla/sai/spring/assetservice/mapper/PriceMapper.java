package vla.sai.spring.assetservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.assetservice.dto.PriceDto;
import vla.sai.spring.assetservice.entity.Price;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceMapper {
    @Mapping(source = "assetId", target = "asset.id")
    Price toEntity(PriceDto priceDto);

    @Mapping(source = "asset.id", target = "assetId")
    PriceDto toDto(Price price);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Price partialUpdate(PriceDto priceDto, @MappingTarget Price price);
}
