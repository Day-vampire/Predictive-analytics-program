package vla.sai.spring.fileservice.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.dto.FileDataDto;
import vla.sai.spring.fileservice.exception.FileNotSavedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static void saveFile(MultipartFile file, FileDataDto fileDataDto) {
        try{
            Path directoryPath = Files.createDirectories(Path.of("Files", fileDataDto.getFileDataType().getValue(), fileDataDto.getFileAuthorName()));
            String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = String.format("%s.%s", baseName, extension);
            Path fullFilePath = Path.of(directoryPath.toString(), fileName);
            file.transferTo(fullFilePath);
        } catch (IOException e) {
            throw new FileNotSavedException("File with name %s not saved.".formatted(file.getOriginalFilename()), e);
        }
    }
}
