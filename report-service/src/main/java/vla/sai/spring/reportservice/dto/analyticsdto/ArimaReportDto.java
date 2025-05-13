package vla.sai.spring.reportservice.dto.analyticsdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArimaReportDto {
    private String dataFileName;
    private String authorName;

    private int analyticColumn;
    private int periods;

    private String parameters; // полученные параметры
}
