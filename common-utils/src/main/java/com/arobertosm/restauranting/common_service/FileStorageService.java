package com.arobertosm.restauranting.common_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path rootLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
        try{
            Files.createDirectories(rootLocation);
        } catch (IOException e){
            throw new RuntimeException("No se pudo inicializar el almacenamiento de archivos.", e);
        }
    }

    public String store(MultipartFile file, String subDirectory, String baseFileName){
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Fallo al guardar un archivo vacío.");
            }

            Path destinationDirectory = this.rootLocation.resolve(subDirectory).normalize();

            Files.createDirectories(destinationDirectory);

            String originalFileName = file.getOriginalFilename();
            String extension = "";
            
            if (baseFileName.contains("..")){
                throw new RuntimeException("Si está viendo este mensaje es porque no está haciendo un buen uso de la aplicación, deje de usarla con fines manlintencionados por favor.");
            }

            if (originalFileName != null && originalFileName.contains(".")){
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            
            String finalFileName = baseFileName + extension;
            Path destinationFile = destinationDirectory.resolve(finalFileName);

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING); 

            return Paths.get(subDirectory, finalFileName).toString().replace("\\", "/");
        } catch (IOException e){
            throw new RuntimeException("Fallo al guardar el archivo.", e);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("El archivo con nombre " + fileName + " no existe.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Archivo no encontrado " + fileName, e);
        }
    }
}
