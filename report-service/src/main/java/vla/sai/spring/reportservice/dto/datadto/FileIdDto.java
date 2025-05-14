package vla.sai.spring.reportservice.dto.datadto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 */
@Value
public class FileIdDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String fileName;
    String fileAuthorName;
}