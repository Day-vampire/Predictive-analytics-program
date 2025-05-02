package vla.sai.spring.fileservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.entity.FileDataType;
import vla.sai.spring.fileservice.entity.FileExtension;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;
import vla.sai.spring.fileservice.entity.FileStatus;
import vla.sai.spring.fileservice.exception.FileAlreadyExistException;
import vla.sai.spring.fileservice.exception.NotFoundException;
import vla.sai.spring.fileservice.repository.FileInfoRepository;
import vla.sai.spring.fileservice.service.FileInfoService;
import vla.sai.spring.fileservice.service.kafka.Producer;
import vla.sai.spring.fileservice.util.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final Producer producer;

    @Override
    @Transactional
    public FileInfo saveFileInfo(MultipartFile file, FileDataDto fileDataDto) throws IOException {

        String fileAuthorName = Optional.ofNullable(fileDataDto.getFileAuthorName())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("fileAuthorName is null or empty"));
        String fullFileName = Optional.ofNullable(file.getOriginalFilename())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("fullFileName is null or empty"));

        FileId fileId = new FileId(FilenameUtils.getBaseName(fullFileName), fileAuthorName);

        if (fileInfoRepository.existsByFileId(fileId))
            throw new FileAlreadyExistException("File with this file name: %s and author name: %s already exists".formatted(fileId.getFileName(), fileAuthorName));

        FileExtension fileExtension = Arrays
                .stream(FileExtension.values())
                .filter(ext -> ext.getValue().equalsIgnoreCase(FilenameUtils.getExtension(fullFileName)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not founded fileExtension: %s [saveFileData]"
                                .formatted(FilenameUtils.getExtension(fullFileName))));

        FileDataType fileDataType = Arrays.stream(FileDataType.values())
                .filter(fdt -> fdt.getValue().equalsIgnoreCase(fileDataDto.getFileDataType().getValue()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Not founded fileDataType: %s [saveFileData]"
                                .formatted(fileDataDto.getFileDataType().getValue())));

        FileUtil.saveFile(file, fileDataDto);
        fileDataDto.setFileName(file.getOriginalFilename());
        if (fileDataDto.getFileDataType().equals(FileDataType.FINANCIAL_ASSERT_STORY)) {
            producer.sendCreatedFileData(file.getBytes(), fileDataDto);
        }
        return fileInfoRepository.save(FileInfo
                .builder()
                .fileId(fileId)
                .fileExtension(fileExtension)
                .fileStatus(FileStatus.VALID)
                .fileSize(BigDecimal.valueOf(file.getSize()))
                .fileType(fileDataType)
                .createTime(LocalDateTime.now())
                .lastModificationTime(LocalDateTime.now())
                .build());
    }

    @Override
    public void deleteFileInfo(FileDataDto fileDataDto) throws IOException {

        String fileAuthorName = Optional.ofNullable(fileDataDto.getFileAuthorName())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("fileAuthorName is null or empty"));
        String fullFileName = Optional.ofNullable(fileDataDto.getFileName())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("fullFileName is null or empty"));
        String fileDataType = Optional.ofNullable(fileDataDto.getFileDataType().getValue())
                .filter(name -> !name.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("fileDataType is null or empty"));

        FileId fileId = new FileId(FilenameUtils.getBaseName(fullFileName), fileAuthorName);

        if (fileInfoRepository.existsByFileId(fileId)) {
            fileInfoRepository.deleteByFileId(fileId);
            producer.sendDeletedFileData(fileDataDto);
        } else throw new NotFoundException("file [%s] not found".formatted(fullFileName));
    }
}

