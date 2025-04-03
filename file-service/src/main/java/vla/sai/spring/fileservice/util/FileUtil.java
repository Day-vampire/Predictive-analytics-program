package vla.sai.spring.fileservice.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static File saveFile(MultipartFile file, String login) {
        try{
            Path directoryPath = Files.createDirectories(Path.of("working_files",login));
            String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = String.format("%s.%s", baseName, extension);
            Path fullFilePath = Path.of(directoryPath.toString(), fileName);
            file.transferTo(fullFilePath);
            return fullFilePath.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
