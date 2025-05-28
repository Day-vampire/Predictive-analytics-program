package vla.sai.spring.reportservice.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import vla.sai.spring.reportservice.dto.ReportIdDto;
import vla.sai.spring.reportservice.dto.ReportInfoDto;
import vla.sai.spring.reportservice.entity.ReportId;
import vla.sai.spring.reportservice.entity.ReportInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportIdMapper {

    ReportIdDto toDto(ReportId entity);

    ReportId toEntity(ReportIdDto dto);
}