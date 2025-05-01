package vla.sai.spring.analyticsservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsDto {
    private String fileName;
    private String AuthorName;
}
