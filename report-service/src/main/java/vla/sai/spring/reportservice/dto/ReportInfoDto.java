package vla.sai.spring.reportservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Value;
import vla.sai.spring.reportservice.entity.ReportFileExtension;
import vla.sai.spring.reportservice.entity.ReportType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Setter

public class ReportInfoDto implements Serializable {
    ReportIdDto reportId;
    @NotNull
    BigDecimal reportFileSize;
    @NotNull
    ReportFileExtension reportFileExtension;
    @NotNull
    ReportType reportType;
    @NotNull
    String reportDataFileName;
    @NotNull
    Boolean isDeleted;
    @NotNull
    LocalDateTime reportCreateTime;
    @NotNull
    LocalDateTime reportLastModificationTime;
}