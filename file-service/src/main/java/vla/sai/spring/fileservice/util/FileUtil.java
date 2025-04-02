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
            String fileName = String.format("%s8%s", baseName, extension);
            Path fullFilePath = Path.of(directoryPath.toString(), fileName);
            file.transferTo(fullFilePath);
            System.out.println("\n\n///////////////////////////// \n\n");
            System.out.println("\n" + directoryPath.toString());
            System.out.println("\n" + fileName + extension + baseName );
            System.out.println("\n" + fullFilePath);
            System.out.println("\n\n///////////////////////////// \n\n");
            return fullFilePath.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
