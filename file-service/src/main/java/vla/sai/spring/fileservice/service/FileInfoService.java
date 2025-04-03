package vla.sai.spring.fileservice.service;

import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.dto.FileInfoDto;

public interface FileInfoService {
    FileInfoDto save(MultipartFile file,  FileDataDto fileDataDto) ;
}
