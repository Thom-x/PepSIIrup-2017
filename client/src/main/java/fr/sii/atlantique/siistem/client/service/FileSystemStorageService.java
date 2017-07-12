package fr.sii.atlantique.siistem.client.service;

import fr.sii.atlantique.siistem.client.exception.SIIStemTechnicalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService {

    @Value("${image.location.path}")
    private String imagesLocation;

    private Path imagesPath;

    @PostConstruct
    private void init() {
        this.imagesPath = Paths.get(imagesLocation);
    }

    public Stream<Path> loadAll() throws IOException {
        return Files.walk(this.imagesPath, 1)
                .filter(path -> !path.equals(this.imagesPath))
                .map(path -> this.imagesPath.relativize(path));
    }

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new SIIStemTechnicalException("Failed to store empty file " + file.getOriginalFilename());
            }
            Path pathToSave = this.imagesPath.resolve(UUID.randomUUID().toString() + ".original" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')));
            Files.copy(file.getInputStream(), pathToSave);
            return pathToSave.toString();
        } catch (IOException e) {
            throw new SIIStemTechnicalException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new SIIStemTechnicalException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new SIIStemTechnicalException("Could not read file: " + filename, e);
        }
    }

    private Path load(String filename) {
        return imagesPath.resolve(filename);
    }
}
