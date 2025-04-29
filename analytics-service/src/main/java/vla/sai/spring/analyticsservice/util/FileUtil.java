package vla.sai.spring.analyticsservice.util;

import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.analyticsservice.dto.FileDataDto;
import vla.sai.spring.analyticsservice.exception.FileNotSavedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static Path saveFile(FileDataDto fileDataDto) {
        try{
            Path directoryPath = Files.createDirectories(Path.of("Files", fileDataDto.getFileDataType().getValue(), fileDataDto.getFileAuthorName()));
            String fileName = fileDataDto.getFileName();
            Path fullFilePath = Path.of(directoryPath.toString(), fileName);
            return fullFilePath;
        } catch (IOException e) {
            throw new FileNotSavedException("File with name %s not saved.".formatted(fileDataDto.getFileName()), e);
        }
    }
}
