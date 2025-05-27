package com.patika.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass // baseEntity lerde kullanırız
public class BaseEntity {
    //private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime createdAt ;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePrePersist(){
        this.createdAt = LocalDateTime.now();
    }


}
