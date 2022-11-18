package com.mykyta.springrestapi.service;

import com.mykyta.springrestapi.model.File;

import java.io.ByteArrayOutputStream;

public interface FileService {
    File findById(Long id);
    void uploadFile(File file);
    StringBuilder listFiles();
    void delete(String fileName);
    ByteArrayOutputStream download(File file);
}
