package vla.sai.spring.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmoothingReportDto {
    private String dataFileName;
    private String authorName;

    private int analyticColumn;
    private int smoothingWindow;

    private String parameters;
}
