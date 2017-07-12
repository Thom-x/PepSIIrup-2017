package fr.sii.atlantique.siistem.client.controller;

import fr.sii.atlantique.siistem.client.exception.SIIStemTechnicalException;
import fr.sii.atlantique.siistem.client.service.FileSystemStorageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class ImageController {

    private final FileSystemStorageService storageService;

    private final RabbitTemplate rabbitTemplate;

    public ImageController(FileSystemStorageService storageService, RabbitTemplate rabbitTemplate) {
        this.storageService = storageService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(ImageController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        String imageName = storageService.store(file);
        rabbitTemplate.convertAndSend("image-stored", imageName);
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler(SIIStemTechnicalException.class)
    public ResponseEntity handleStorageFileNotFound() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
