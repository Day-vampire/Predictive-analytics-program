package vla.sai.spring.analyticsservice.dto;


import lombok.Builder;
import lombok.Data;
import vla.sai.spring.analyticsservice.entity.FileDataType;

import java.io.Serializable;

@Data
@Builder
public class FileDataDto implements Serializable {
    private String fileAuthorName;
    private String fileName;
    private FileDataType fileDataType;
}
