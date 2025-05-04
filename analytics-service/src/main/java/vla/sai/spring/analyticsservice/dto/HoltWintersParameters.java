package vla.sai.spring.analyticsservice.dto;


import lombok.Builder;
import lombok.Data;

/**
 analyticColumn - анализируемого столбца
 seasonLength - Длина сезона
 nPreds - Горизонт прогноза
 */
@Data
@Builder
public class HoltWintersParameters {
    private String dataFileName;
    private String authorName;
    private int analyticColumn;
    private int seasonLength;
    private int nPreds;
}
