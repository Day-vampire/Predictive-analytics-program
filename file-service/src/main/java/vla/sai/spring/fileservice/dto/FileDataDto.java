package vla.sai.spring.fileservice.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vla.sai.spring.fileservice.entity.FileDataType;

import java.io.Serializable;

@Data
public class FileDataDto implements Serializable {
    @NotNull
    private String fileAuthorName;
    @NotNull
    private FileDataType fileDataType;
}
