package com.patika.service;

import com.patika.repository.ImageFileRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageFileService {
    private  final ImageFileRepository imageFileRepository;

    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }
}
