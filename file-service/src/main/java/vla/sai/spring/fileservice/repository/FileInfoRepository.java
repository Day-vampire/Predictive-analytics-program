package vla.sai.spring.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, FileId> {

    FileInfoDto save(FileInfo fileInfo);
}