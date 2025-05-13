package vla.sai.spring.reportservice.dto.analyticsdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// класс для передачи данных в сервис отчетов
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcfPacfReportDto {
    private String dataFileName;
    private String authorName;
    private int analyticColumn;
    private int analyticLags;
    private String parameters; // полученные параметры
}
