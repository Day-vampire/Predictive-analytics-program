package vla.sai.spring.fileservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.dto.FilterParameters;
import vla.sai.spring.fileservice.entity.FileDataType;
import vla.sai.spring.fileservice.entity.FileExtension;
import vla.sai.spring.fileservice.entity.FileId;
import vla.sai.spring.fileservice.entity.FileInfo;
import vla.sai.spring.fileservice.entity.FileStatus;
import vla.sai.spring.fileservice.exception.FileAlreadyExistException;
import vla.sai.spring.fileservice.exception.NotFoundException;
import vla.sai.spring.fileservice.mapper.FileInfoMapper;
import vla.sai.spring.fileservice.repository.FileInfoRepository;
import vla.sai.spring.fileservice.service.FileInfoService;
import vla.sai.spring.fileservice.service.kafka.Producer;
import vla.sai.spring.fileservice.util.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final FileInfoMapper fileInfoMapper;
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

    @Transactional
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
            log.info("File [%s] of user [%s] deleted from data base".formatted(fullFileName, fileAuthorName));
            Files.delete(Path.of("file-service/src/main/resources/Files", fileDataType, fileAuthorName,fullFileName));
            log.info("File [%s] deleted from user directory: [%s] ".formatted(fullFileName, fileAuthorName));
            producer.sendDeletedFileData(fileDataDto);
            log.info("producer send delete massage for file [%s] ".formatted(fullFileName));
        } else throw new NotFoundException("file [%s] not found".formatted(fullFileName));
    }

    @Transactional
    @Override
    public void deleteAllByFilesAuthorName(String fileAuthorName) throws IOException {
        if (fileInfoRepository.existsByFileId_FileAuthorName(fileAuthorName)) {
            fileInfoRepository.deleteAllByFileId_FileAuthorName(fileAuthorName);
            log.info("All files of user [%s] deleted from data base".formatted(fileAuthorName));
            Files.deleteIfExists(Path.of("file-service/src/main/resources/Files/financial_assert_story", fileAuthorName));
            log.info("All files of user [%s] deleted from user directory financial_assert_story".formatted(fileAuthorName));
        } else throw new NotFoundException("files of user [%s] not found in database".formatted(fileAuthorName));
    }

    @Override
    public Page<FileInfoDto> findAll(Pageable pageable) {
        return fileInfoRepository.findAll(pageable).map(fileInfoMapper::toDto);
    }

    @Override
    public Page<FileInfoDto> findAllByFileAuthorName(String fileAuthorName, Pageable pageable) {
        return fileInfoRepository.findAllByFileId_FileAuthorName(fileAuthorName, pageable).map(fileInfoMapper::toDto);
    }

    public Page<FileInfoDto> search(FilterParameters filterParameters) {
        List<Sort.Order> orders = new ArrayList();
        if (!filterParameters.getSortDirection().isEmpty()) {
            filterParameters.getSortDirection().forEach((columnName, direction) ->
                    orders.add(new Sort.Order(direction, columnName))
            );
        } else {
            orders.add(new Sort.Order(null, FileInfo_.ID));
        }
        PageRequest pageRequest = PageRequest.of(filterParameters.getPage(), filterParameters.getRows(), by(orders));
        Page<FileInfo> rawResult;
        if (filterParameters.hasSearchText()) {
            rawResult = fileInfoRepository.search(filterParameters.getSearchText(), pageRequest);
        } else {
            rawResult = fileInfoRepository.fetchDataByPage(pageRequest);
        }
        Page<FileInfoDto> mapped = rawResult.map(fileInfoMapper::toDto);
        if (filterParameters.hasSearchText() && rawResult.getTotalElements() > 100000) {
            return new PageImpl<>(mapped.getContent(), pageRequest, 100000);
        } else {
            return new PageImpl<>(mapped.getContent(), pageRequest, rawResult.getTotalElements());
        }
    }

}

