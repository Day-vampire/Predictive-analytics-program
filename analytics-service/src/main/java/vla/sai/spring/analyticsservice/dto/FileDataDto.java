package vla.sai.spring.analyticsservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vla.sai.spring.analyticsservice.entity.FileDataType;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDataDto implements Serializable {
    private String fileAuthorName;
    private String fileName;
    private FileDataType fileDataType;
}
