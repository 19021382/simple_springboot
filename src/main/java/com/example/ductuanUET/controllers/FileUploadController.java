package com.example.ductuanUET.controllers;

import com.example.ductuanUET.models.ResponseObject;
import com.example.ductuanUET.services.IStorageService;
import com.example.ductuanUET.services.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    //Inject Storage Service here
    @Autowired
    private IStorageService storageService;
    //This controller receive file/image from client
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            //save files to folder => use a service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","upload file successfully", generatedFileName)
            );
            //how to open 83751039dcb947059c4162544f856af3.jpg in web browser?
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok",exception.getMessage(), ""));
        }
    }

    //get image's url
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);

        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }
    //How to load all uploaded files ?
    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadedFiles() {
        try {
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        //convert fileName to url(send request "readDetailFile")
                String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,"readDetailFile", path.getFileName().toString()).build().toUri().toString();
                return urlPath;
            }).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok", "List file successfully",urls));
        } catch (Exception exception) {
            return ResponseEntity.ok(new ResponseObject("failed", "List files failed",new String[] {}));
        }
    }
}
