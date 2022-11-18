package com.mykyta.springrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mykyta.springrestapi.model.File;
import com.mykyta.springrestapi.model.Status;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FileDto {
    private Long id;

    private String filename;

    private String location;

    private Status status;

    public File toFile(){
        File file = new File();
        file.setId(id);
        file.setFileName(filename);
        file.setLocation(location);
        file.setStatus(status);
        return file;
    }

    public static FileDto fromFile(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setFilename(file.getFileName());
        fileDto.setLocation(file.getLocation());
        fileDto.setStatus(file.getStatus());
        return fileDto;
    }

}
