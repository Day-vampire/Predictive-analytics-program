package vla.sai.spring.reportservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 */
@Value
public class ReportIdDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String reportName;
    String reportAuthorName;
}