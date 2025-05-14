package vla.sai.spring.reportservice.dto.analyticsdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

// класс для передачи данных в сервис отчетов
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoltWintersReportDto {

    private String dataFileName;
    private String authorName;

    private int analyticColumn;
    private int seasonLength;
    private int periods;

    private String parameters; // полученные параметры
}
