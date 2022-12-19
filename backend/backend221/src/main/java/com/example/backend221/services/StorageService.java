package com.example.backend221.services;

import com.example.backend221.Exception.FileStoreException;
import com.example.backend221.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private final Path fileStorageLocation;

    @Autowired
    public StorageService(StorageProperties storageProperties) {
        this.fileStorageLocation = Paths.get(storageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStoreException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public Resource loadAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStoreException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStoreException("File not found " + fileName,ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStoreException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStoreException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public String deleteFile(String fileName) {
        try {
            if (fileName.contains("..")) {
                return "Sorry! Filename contains invalid path sequence " + fileName;
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.delete(targetLocation);
            return fileName + " deleted!";
        } catch (IOException ex) {
            return fileName + " not found!";
        }
    }
//    public void deleteFileById(Integer bookingId){
//        String dest = bookingId.toString();
//        FileSystemUtils.deleteRecursively(fileStorageLocation.resolve(dest).toFile());
//    }
}
