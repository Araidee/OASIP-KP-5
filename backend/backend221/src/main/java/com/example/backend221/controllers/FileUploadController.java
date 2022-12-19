package com.example.backend221.controllers;

import com.example.backend221.entities.Event;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
@RestControllerAdvice
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private StorageService storageService;
    @Autowired
    private EventRepository eventRepository;



    @GetMapping("/get/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {

        Resource resource = storageService.loadAsResource(filename);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }



    @PostMapping("/upload-file")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = storageService.storeFile(file);
        System.out.println(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(fileName + " uploaded!");
    }
    @DeleteMapping("/{filename:.+}")
    public ResponseEntity deleteFile(@PathVariable String filename) {
        System.out.println(filename);
        Event event = eventRepository.findEventByAttachment(filename);
        System.out.println(event.getId());
        if(event != null){
            eventRepository.updateAttachment(event.getId(),null);
        }
        String ans = storageService.deleteFile(filename);
//                StorageService.deleteFile(filename);
        return ResponseEntity.status(HttpStatus.OK).body(ans);

    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String fileSizeExceptionHandler(RedirectAttributes ra) {
        return "File size should be less than 10MB!";
    }

}

