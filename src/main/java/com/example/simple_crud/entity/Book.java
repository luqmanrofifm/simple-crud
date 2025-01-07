package com.example.simple_crud.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity(name = "book")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(
            generator = "uuid"
    )
    @UuidGenerator
    private UUID id;
    @NotNull
    private Date createdDate;
    private Date updatedDate;
    @NotNull
    private String createdBy;
    private String updatedBy;

    private String title;
    private String author;
    private Integer year;
}
