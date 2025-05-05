package vla.sai.spring.fileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.dto.FileInfoDto;
import vla.sai.spring.fileservice.entity.FileDataType;
import vla.sai.spring.fileservice.entity.FileInfo;
import vla.sai.spring.fileservice.service.FileInfoService;

import java.io.IOException;


@RestController
@RequestMapping(value = "/files")
@RequiredArgsConstructor
public class FileController {

    private final FileInfoService fileInfoService;

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "" +
            "Загрузка файла", description = "Загружает файл и сохраняет его на сервере.")
    public FileInfo uploadFile(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam(value = "type", required = false) FileDataType fileDataType,
            @RequestParam(value = "authName", required = false) String fileAuthorName) throws IOException {
        return fileInfoService.saveFileInfo(file, FileDataDto.builder().fileDataType(fileDataType).fileAuthorName(fileAuthorName).build());
    }

    @PostMapping(path = "/delete-file")
    @Operation(summary = "Удаление файла пользователя", description = "Удаляет файл из директории и БД")
    public void deleteFile(FileDataDto fileDataDto) throws IOException {
        fileInfoService.deleteFileInfo(fileDataDto);
    }

    @PostMapping(path = "/delete-user-files")
    @Operation(summary = "Удаление всех файлов пользователя", description = "Удаляет файлы из директории и БД")
    public void deleteAllFilesByAuthorName(String fileAuthorName) throws IOException {
        fileInfoService.deleteAllByFilesAuthorName(fileAuthorName);
    }

    @GetMapping
    @Operation(summary = "Получение всех файлов", description = "Получает все файлы из БД")
    public Page<FileInfoDto> getAllFiles(@PageableDefault(size = 10,page = 0) Pageable pageable) {
       return fileInfoService.findAll(pageable);
    }

    @GetMapping(path = "/user-files")
    @Operation(summary = "Получение файлов пользователя ", description = "Получает все файлы пользователя из БД")
    public Page<FileInfoDto> getAllFilesByAuthorName(String authorName, @PageableDefault(size = 10,page = 0) Pageable pageable) {
        return fileInfoService.findAllByFileAuthorName(authorName, pageable);
    }

}