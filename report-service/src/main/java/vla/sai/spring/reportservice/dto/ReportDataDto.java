package vla.sai.spring.reportservice.dto;


import lombok.Builder;
import lombok.Data;
import vla.sai.spring.reportservice.entity.ReportType;

import java.io.Serializable;

@Data
@Builder
public class ReportDataDto implements Serializable {
    private String fileAuthorName;
    private String fileName;
    private ReportType reportType;
}
