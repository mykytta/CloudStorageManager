package com.mykyta.springrestapi.repository;

import com.mykyta.springrestapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    File getFileByFileName(String filename);
}
