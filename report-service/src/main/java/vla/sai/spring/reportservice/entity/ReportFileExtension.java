package vla.sai.spring.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportFileExtension {
    XLS("xls"),
    XLSX("xlsx"),
    PDF("pdf");
    private final String value;
}
