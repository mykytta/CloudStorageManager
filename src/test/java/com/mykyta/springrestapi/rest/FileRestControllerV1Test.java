package com.mykyta.springrestapi.rest;

import com.mykyta.springrestapi.dto.FileDto;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FileRestControllerV1Test {

    @MockBean
    private FileService fileService;

    @Autowired
    private FileRestControllerV1 fileRestControllerV1;

    @Test
    void upload() {
        File expected = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        fileRestControllerV1.upload(FileDto.fromFile(expected));
        verify(fileService).uploadFile(expected);
        verify(fileService, times(1)).uploadFile(expected);

    }

    @Test
    void update() {
        File expected = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        expected.setId(1L);
        fileRestControllerV1.update(FileDto.fromFile(expected));
        verify(fileService).findById(1L);
        verify(fileService, times(1)).findById(1L);
    }

    @Test
    void listFiles() {
        fileRestControllerV1.listFiles();
        verify(fileService).listFiles();
        verify(fileService, times(1)).listFiles();
    }

    @Test
    void delete() {
        File expected = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        fileRestControllerV1.delete(expected.getFileName());
        verify(fileService).delete(expected.getFileName());
        verify(fileService, times(1)).delete("cat");
    }
}