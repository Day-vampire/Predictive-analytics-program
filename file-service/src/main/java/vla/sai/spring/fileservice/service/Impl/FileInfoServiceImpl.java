package vla.sai.spring.fileservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.entity.FileExtension;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;
import vla.sai.spring.fileservice.entity.FileStatus;
import vla.sai.spring.fileservice.repository.FileInfoRepository;
import vla.sai.spring.fileservice.service.FileInfoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private FileInfoRepository fileInfoRepository;

    @Override
    @Transactional
    public FileInfoDto save(MultipartFile file, FileDataDto fileDataDto) {
        FileInfo fileInfo = FileInfo
                .builder()
                .fileId(FileId
                        .builder()
                        .fileName(FilenameUtils.getBaseName(file.getOriginalFilename()))
                        .fileAuthorName(fileDataDto.getFileAuthorName())
                        .build())
                .fileExtension(Arrays
                        .stream(FileExtension.values())
                        .filter(ext -> ext.getValue().equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename())))
                        .findFirst().orElse(FileExtension.NON))
                .id(1L)
                .fileSize(BigDecimal.valueOf(file.getSize()))
                .fileStatus(FileStatus.NEW)
                .fileType(fileDataDto.getFileDataType())
                .createTime(LocalDateTime.now())
                .lastModificationTime(LocalDateTime.now())
                .build();
        fileInfo.setFileStatus((fileInfo.getFileExtension().getValue().equals(FileExtension.NON.getValue()))?FileStatus.NOT_VALID:FileStatus.VALID);
        return fileInfoRepository.save(fileInfo);
        }
    }
