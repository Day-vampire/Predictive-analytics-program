package vla.sai.spring.fileservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, FileId> {

    FileInfo save(FileInfo fileInfo);
    List<FileInfo> findAll();
    Boolean existsByFileId(FileId fileId);
    void deleteByFileId(FileId fileId);
}