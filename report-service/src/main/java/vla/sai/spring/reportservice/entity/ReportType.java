package vla.sai.spring.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    DATA_REPORT_PDF("data_report_pdf"),
    DATA_REPORT_EXCEL("data_report_excel"),
    SMOOTHING_GRAPH_REPORT_PDF("smoothing_graph_report_pdf"),
    HOLT_WINTERS_REPORT_PDF("holt_winters_report_pdf"),
    ACF_PACF_REPORT_PDF("acf_pacf_report_pdf"),
    ARIMA_REPORT_PDF("arima_report_pdf"),
    SARIMA_REPORT_PDF("sarima_report_pdf");
    private final String value;
}
