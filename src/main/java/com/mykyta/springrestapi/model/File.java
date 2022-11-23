package com.mykyta.springrestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseEntity{
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "location")
    private String location;
}
