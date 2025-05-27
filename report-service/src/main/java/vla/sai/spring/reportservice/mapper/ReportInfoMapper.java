package vla.sai.spring.reportservice.mapper;

import org.mapstruct.*;
import vla.sai.spring.reportservice.dto.ReportInfoDto;
import vla.sai.spring.reportservice.entity.ReportInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportInfoMapper {
    ReportInfo toEntity(ReportInfoDto reportInfoDto);

    ReportInfoDto toDto(ReportInfo reportInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ReportInfo partialUpdate(ReportInfoDto reportInfoDto, @MappingTarget ReportInfo reportInfo);
}