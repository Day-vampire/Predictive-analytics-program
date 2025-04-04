package vla.sai.spring.fileservice.dto;


import lombok.Builder;
import lombok.Data;
import vla.sai.spring.fileservice.entity.FileDataType;

import java.io.Serializable;

@Data
@Builder
public class FileDataDto implements Serializable {
    private String fileAuthorName;
    private FileDataType fileDataType;
}
