package vla.sai.spring.analyticsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmoothingParameters {
    private String dataFileName;
    private String authorName;
    private int analyticColumn;
    private int smoothingWindow;
}
