package vla.sai.spring.analyticsservice.dto;

import lombok.Builder;
import lombok.Data;

// класс для передачи данных в сервис отчетов
@Data
@Builder
public class AcfPacfReportDto {
    private String dataFileName;
    private String authorName;
    private int analyticColumn;
    private int analyticLags;
    private String parameters; // полученные параметры
}
