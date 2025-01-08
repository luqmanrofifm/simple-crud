package com.example.simple_crud.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(
            generator = "uuid"
    )
    @UuidGenerator
    private UUID id;
    @NotNull
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;

    @NotNull
    private String name;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @JsonIgnore
    private String password;
}
