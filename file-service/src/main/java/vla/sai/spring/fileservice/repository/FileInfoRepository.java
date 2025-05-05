package vla.sai.spring.fileservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;

import java.util.List;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, FileId> {

    FileInfo save(FileInfo fileInfo);

    List<FileInfo> findAll();
    Page<FileInfo> findAll(Pageable pageable);
    Page<FileInfo> findAllByFileId_FileAuthorName(String fileIdFileAuthorName, Pageable pageable);

    Boolean existsByFileId(FileId fileId);
    Boolean existsByFileId_FileAuthorName(String fileIdFileAuthorName);

    void deleteByFileId(FileId fileId);
    void deleteAllByFileId_FileAuthorName(String fileIdFileAuthorName);

}