package vla.sai.spring.fileservice.controller;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vla.sai.spring.fileservice.entity.FileInfo;
import vla.sai.spring.fileservice.entity.FileInfo_;
import vla.sai.spring.fileservice.exeption.StorageFileNotFoundException;
import vla.sai.spring.fileservice.service.StorageService;


@RestController
public class FileUploadController {

    private final StorageService storageService;
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }











    @GetMapping("/test_generator")
    public Map<String,String> testGenerator() {
        return Stream.of(FileInfo_.class.getDeclaredFields()) // достает атрибуты метамодели
                .filter(field -> Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) // фильтрация на отбор final/static атрибуты
                .collect(Collectors.toMap(
                        Field::getName,
                        field -> {
                            try { return (String) field.get(null);}
                            catch (IllegalAccessException e) { throw new RuntimeException(e);}
                        }
                        )
                );
    }













    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}