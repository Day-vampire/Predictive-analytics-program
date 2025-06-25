package vla.sai.spring.reportservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 */
@Setter
@Value
public class ReportIdDto implements Serializable {
    @NotNull
    @NotBlank
    String reportName;
    String reportAuthorName;
}