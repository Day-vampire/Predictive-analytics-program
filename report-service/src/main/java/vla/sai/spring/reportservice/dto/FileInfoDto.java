package vla.sai.spring.reportservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Value;
import vla.sai.spring.fileservice.entity.FileDataType;
import vla.sai.spring.fileservice.entity.FileExtension;
import vla.sai.spring.fileservice.entity.FileStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link vla.sai.spring.fileservice.entity.FileInfo}
 */
@Value
@Setter
public class FileInfoDto implements Serializable {
    FileIdDto fileId;
    @NotNull
    BigDecimal fileSize;
    @NotNull
    FileExtension fileExtension;
    @NotNull
    FileStatus fileStatus;
    @NotNull
    FileDataType fileType;
    @NotNull
    Boolean isDeleted;
    @NotNull
    LocalDateTime createTime;
    @NotNull
    LocalDateTime lastModificationTime;
}