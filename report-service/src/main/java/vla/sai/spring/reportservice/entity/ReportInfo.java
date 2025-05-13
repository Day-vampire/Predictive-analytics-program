package vla.sai.spring.reportservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class ReportInfo {

    @EmbeddedId
    private ReportId reportId;

    @Column(name ="report_file_size", nullable = false, precision = 10, scale = 2)
    private BigDecimal reportFileSize;

    @Enumerated(EnumType.STRING)
    @Column(name ="report_file_extension", nullable = false)
    private ReportFileExtension reportFileExtension;

    @Enumerated(EnumType.STRING)
    @Column(name ="report_type", nullable = false)
    private ReportType reportType;

    @Column(name= "report_data_file_name")
    private String reportDataFileName;

    @Column(name ="deleted", columnDefinition = "false")
    private Boolean isDeleted = false;

    @Column(name ="report_create_time", nullable = false)
    private LocalDateTime reportCreateTime;

    @Column(name ="report_last_modification_time", nullable = false)
    private LocalDateTime reportLastModificationTime;
}
