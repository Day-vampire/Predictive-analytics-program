package vla.sai.spring.analyticsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArimaReportDto {
    private String dataFileName;
    private String authorName;

    private int analyticColumn;
    private int periods;

    private String parameters; // полученные параметры
}
