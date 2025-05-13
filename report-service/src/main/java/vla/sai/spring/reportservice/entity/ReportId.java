package vla.sai.spring.reportservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportId {

    @Column(name = "report_name", nullable = false)
    private String reportName;

    @Column(name = "report_author_name", nullable = false)
    private String reportAuthorName;
}
