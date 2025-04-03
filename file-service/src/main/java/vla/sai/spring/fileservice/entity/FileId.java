package vla.sai.spring.fileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileId {

    @Column(name = "flname", nullable = false)
    private String fileName;

    @Column(name = "flauthor", nullable = false)
    private String fileAuthorName;
}
