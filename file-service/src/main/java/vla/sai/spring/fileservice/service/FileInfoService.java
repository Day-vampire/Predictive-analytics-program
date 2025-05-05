package vla.sai.spring.fileservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.entity.FileInfo;

import java.io.IOException;

public interface FileInfoService {
    FileInfo saveFileInfo(MultipartFile file, FileDataDto fileDataDto) throws IOException;

    void deleteFileInfo(FileDataDto fileDataDto) throws IOException;
    void deleteAllByFilesAuthorName(String fileAuthorName) throws IOException;

    Page<FileInfoDto> findAll(Pageable pageable);
    Page<FileInfoDto> findAllByFileAuthorName(String fileAuthorName, Pageable pageable);
}
