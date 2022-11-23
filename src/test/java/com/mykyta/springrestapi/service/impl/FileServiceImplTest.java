package com.mykyta.springrestapi.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.mykyta.springrestapi.model.Event;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.model.User;
import com.mykyta.springrestapi.repository.FileRepository;
import com.mykyta.springrestapi.service.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FileServiceImplTest {

    @MockBean
    private FileRepository fileRepository;
    @Autowired
    private FileService fileService;

    @Test
    void findById() {
        when(fileRepository.getById(2L)).thenReturn(new File("cat", "cat.jpg"));
        File test = fileService.findById(2L);
        assertNotNull(test);
        assertEquals(fileRepository.getById(2L), test);
        assertNull(fileService.findById(1L));
    }

    @Test
    void uploadFile() {
        File expected = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        fileService.uploadFile(expected);
        verify(fileRepository).save(expected);
        verify(fileRepository, times(1)).save(expected);
    }

    @Test
    void delete() {
        File expected = new File("cat", "/Users/niqitagrigorivich/Downloads/REST-API-JWT/src/test/java/com/mykyta/springrestapi/filetotest/cat.jpg");
        when(fileRepository.getFileByFileName("cat")).thenReturn(expected);
        fileService.delete("cat");
        assertEquals(Status.DELETED, expected.getStatus());
    }

    @Test
    void getAll() {
        when(fileRepository.findAll()).thenReturn(Stream
                .of(new File("cat", "cat.jpg"),
                        new File("dog", "dog.jpg")).collect(Collectors.toList()));
        assertEquals(2, fileService.getAll().size());
        assertNotNull(fileService.getAll());
    }
}