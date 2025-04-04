package vla.sai.spring.fileservice.service;

import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.entity.FileInfo;

public interface FileInfoService {
    FileInfo saveFileInfo(MultipartFile file, FileDataDto fileDataDto) ;
}
