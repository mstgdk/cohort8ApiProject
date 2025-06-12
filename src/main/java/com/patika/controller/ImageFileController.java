package com.patika.controller;

import com.patika.dto.response.ImageSavedResponse;
import com.patika.dto.response.ImagefileDto;
import com.patika.dto.response.PatikaResponse;
import com.patika.entity.ImageFile;
import com.patika.service.ImageFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageFileController {

    private final ImageFileService imagefileService;

    public ImageFileController(ImageFileService imagefileService) {
        this.imagefileService = imagefileService;
    }
    //UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("file")MultipartFile file){

        String imageId = imagefileService.saveImage(file);

        ImageSavedResponse response =  new ImageSavedResponse("successully saaved",true,imageId);
        return  ResponseEntity.ok(response);
    }
    //Image Display
    @GetMapping("/display/{id}")
    public  ResponseEntity<byte[]>displayImage(@PathVariable String id){
        ImageFile imageFile = imagefileService.getImageById(id);

       HttpHeaders header = new HttpHeaders();
       header.setContentType(MediaType.IMAGE_PNG);
       return new ResponseEntity<>(imageFile.getData(),header,HttpStatus.OK);


    }
    //Image Display
    @GetMapping("/download/{id}")
    public  ResponseEntity<byte[]>downloadImage(@PathVariable String id){
        ImageFile imageFile = imagefileService.getImageById(id);

        return ResponseEntity.ok().header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;fileName=" + imageFile.getName()).
                body(imageFile.getData());


    }
    //delte
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PatikaResponse> removeFile(@PathVariable String id){

        imagefileService.remove(id);

        PatikaResponse response =  new PatikaResponse("deleted succesfully",true);
        return  ResponseEntity.ok(response);
    }
    //getAll
    @GetMapping
    public ResponseEntity<List<ImagefileDto>>getAllImages(){
        List<ImagefileDto> allImages =  imagefileService.getAllImages();
        return ResponseEntity.ok(allImages);
    }

}
