package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.FileDto;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.service.FileService;
import com.mykyta.springrestapi.utils.ContentTypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileRestControllerV1 {
    private final FileService fileService;

    @Autowired
    public FileRestControllerV1(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> upload(@RequestBody FileDto file){
        fileService.uploadFile(file.toFile());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/upload")
    public ResponseEntity<?> update(@RequestBody FileDto file){
        if(fileService.findById(file.getId()) != null) {
            File fileToUpdate = fileService.findById(file.getId());
            fileService.uploadFile(fileToUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("We cannot find file with this id" , HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/download")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> download(@RequestBody FileDto file){
        ByteArrayOutputStream downloadInputStream = fileService.download(file.toFile());

        return ResponseEntity.ok()
                .contentType(ContentTypeChecker.contentType(file.getLocation()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(downloadInputStream.toByteArray());
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> listFiles(){
        return new ResponseEntity<>(fileService.listFiles(), HttpStatus.OK);
    }

    @DeleteMapping("{filename}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable(name = "filename") String filename){
        fileService.delete(filename);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
