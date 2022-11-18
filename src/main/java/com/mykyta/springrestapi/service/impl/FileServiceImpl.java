package com.mykyta.springrestapi.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.model.Status;
import com.mykyta.springrestapi.repository.FileRepository;
import com.mykyta.springrestapi.service.FileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final AmazonS3 s3client;

    private final FileRepository fileRepository;

    private final String bucketName = "vasylkitaws";

    @Autowired
    public FileServiceImpl(AmazonS3 s3client, FileRepository fileRepository) {
        this.s3client = s3client;
        this.fileRepository = fileRepository;
    }

    public void createBucket() {

        if (s3client.doesBucketExistV2(bucketName)) {
            log.info("Bucket {} already exists, use a different name", bucketName);
            return;
        }

        s3client.createBucket(bucketName);
    }

    @Override
    public File findById(Long id) {
        return fileRepository.getById(id);
    }

    @Override
    @SneakyThrows
    public void uploadFile(File file) {
        if (!s3client.doesBucketExistV2(bucketName)) {
            createBucket();
        }
        java.io.File fileForUpload = new java.io.File(file.getLocation());
        s3client.putObject(bucketName, file.getFileName(), fileForUpload);
        file.setStatus(Status.ACTIVE);
        fileRepository.save(file);
    }

    @Override
    public StringBuilder listFiles() {
        ObjectListing objects = s3client.listObjects(bucketName);
        StringBuilder summary = new StringBuilder();
        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
            summary.append(objectSummary.getKey());
        }
        return summary;
    }


    @Override
    public void delete(String fileName) {
        File file = fileRepository.getFileByFileName(fileName);
        if(file != null){
            s3client.deleteObject(bucketName, fileName);
            file.setStatus(Status.DELETED);
            fileRepository.save(file);
        }
    }


    @Override
    @SneakyThrows
    public ByteArrayOutputStream download(File file) {
        S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, file.getFileName()));

        InputStream is = s3object.getObjectContent();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[4096];
        while ((len = is.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, len);
        }

        return outputStream;
    }
}
