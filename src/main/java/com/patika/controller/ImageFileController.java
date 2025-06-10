package com.patika.controller;

import com.patika.service.ImageFileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageFileController {

    private final ImageFileService imagefileService;

    public ImageFileController(ImageFileService imagefileService) {
        this.imagefileService = imagefileService;
    }
}
