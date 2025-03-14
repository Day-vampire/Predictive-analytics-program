package vla.sai.spring.fileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class FileInfo {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @EmbeddedId
    private FileId fileId;

    private Long fileSize;
    private FileExtension fileExtension;
    private FileStatus fileStatus;
    private FileDataType fileType;

    @Column(name ="deleted", columnDefinition = "false")
    private Boolean isDeleted = false;
}
