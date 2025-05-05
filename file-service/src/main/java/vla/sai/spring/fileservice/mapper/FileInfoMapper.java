package vla.sai.spring.fileservice.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.entity.FileInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileInfoMapper {
    FileInfo toEntity(FileInfoDto fileInfoDto);

    FileInfoDto toDto(FileInfo fileInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FileInfo partialUpdate(FileInfoDto fileInfoDto, @MappingTarget FileInfo fileInfo);
}