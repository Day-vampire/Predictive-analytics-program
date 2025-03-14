package vla.sai.spring.fileservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FileId {
    private String fileName;
    private String fileAuthorName;
}
