package vla.sai.spring.fileservice.service;

import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.entity.FileInfo;

import java.io.IOException;

public interface FileInfoService {
    FileInfo saveFileInfo(MultipartFile file, FileDataDto fileDataDto) throws IOException;
    void deleteFileInfo(FileDataDto fileDataDto) throws IOException;
}
