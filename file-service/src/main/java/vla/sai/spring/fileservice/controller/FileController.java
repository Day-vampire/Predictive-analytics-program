package vla.sai.spring.fileservice.controller;

import java.io.File;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vla.sai.spring.fileservice.service.StorageService;
import vla.sai.spring.fileservice.util.FileUtil;


@RestController
@RequestMapping (value ="/files")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;

    @PostMapping("/upload-file")
    @Operation(summary = "Обновление файла")
    public void uploadFile (@RequestParam("file") MultipartFile file) {
        File uploadedFile = FileUtil.saveFile(file,"users");
    }
//
//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                        path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
//                                "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//
//        if (file == null)
//            return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//    @PostMapping("/")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/test_generator")
//    public Map<String,String> testGenerator() {
//        return Stream.of(FileInfo_.class.getDeclaredFields()) // достает атрибуты метамодели
//                .filter(field -> Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) // фильтрация на отбор final/static атрибуты
//                .collect(Collectors.toMap(
//                        Field::getName,
//                        field -> {
//                            try { return (String) field.get(null);}
//                            catch (IllegalAccessException e) { throw new RuntimeException(e);}
//                        }
//                        )
//                );
//    }
//

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
//
}