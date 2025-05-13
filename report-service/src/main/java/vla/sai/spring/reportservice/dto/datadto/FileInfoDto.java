package vla.sai.spring.reportservice.dto.datadto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
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