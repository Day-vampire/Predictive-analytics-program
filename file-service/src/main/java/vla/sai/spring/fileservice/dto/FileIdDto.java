package vla.sai.spring.fileservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vla.sai.spring.fileservice.entity.FileId}
 */
@Value
public class FileIdDto implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    String fileName;
    String fileAuthorName;
}