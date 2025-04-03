package vla.sai.spring.fileservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileExtension {
    DOC("doc"),
    DOCX("docs"),
    SCV("csv"),
    TXT("txt"),
    XLS("xls"),
    XLSX("xlsx"),
    PDF("pdf"),
    NON("non");

    private final String value;
}
