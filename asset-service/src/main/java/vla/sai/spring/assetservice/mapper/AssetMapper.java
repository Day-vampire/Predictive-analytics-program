package vla.sai.spring.assetservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.assetservice.dto.AssetDto;
import vla.sai.spring.assetservice.entity.Asset;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssetMapper {
    Asset toEntity(AssetDto assetDto);

    AssetDto toDto(Asset asset);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Asset partialUpdate(AssetDto assetDto, @MappingTarget Asset asset);
}