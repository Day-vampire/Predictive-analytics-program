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
public class FileInfo {

    @EmbeddedId
    private FileId fileId;

    @Column(name ="flsize", nullable = false, precision = 10, scale = 2)
    private BigDecimal fileSize;

    @Enumerated(EnumType.STRING)
    @Column(name ="flext", nullable = false)
    private FileExtension fileExtension;

    @Enumerated(EnumType.STRING)
    @Column(name ="flstatus", nullable = false)
    private FileStatus fileStatus;

    @Enumerated(EnumType.STRING)
    @Column(name ="fldatatype", nullable = false)
    private FileDataType fileType;

    @Column(name ="deleted", columnDefinition = "false")
    private Boolean isDeleted = false;

    @Column(name ="flcrtetm", nullable = false)
    private LocalDateTime createTime;

    @Column(name ="fllstmodtm", nullable = false)
    private LocalDateTime lastModificationTime;
}
