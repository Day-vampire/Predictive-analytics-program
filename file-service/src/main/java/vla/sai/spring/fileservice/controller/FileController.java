package vla.sai.spring.fileservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
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
    @Operation(summary = "Обновление/загрузка файла", description = "Загружает файл и обновляет/сохраняет его на сервере.")
    public FileInfo uploadFile(
            @RequestParam(value = "file",required = false) MultipartFile file,
            @RequestParam(value = "type", required = false) FileDataType fileDataType,
            @RequestParam(value = "authName", required = false) String fileAuthorName) throws IOException {
        return fileInfoService.saveFileInfo(file, FileDataDto.builder().fileDataType(fileDataType).fileAuthorName(fileAuthorName).build());
    }
}