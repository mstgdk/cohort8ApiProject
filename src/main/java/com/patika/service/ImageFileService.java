package com.patika.service;

import com.patika.dto.response.ImagefileDto;
import com.patika.entity.ImageFile;
import com.patika.exception.ResourceNotFoundException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ImageFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageFileService {
    private  final ImageFileRepository imageFileRepository;

    public ImageFileService(ImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    //SAVE
    public String saveImage(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getName()));

            String fileType = file.getContentType();

            long length = file.getSize();

            byte[] data = file.getBytes();

            ImageFile imageFile = new ImageFile(fileName,fileType,length,data);
            imageFileRepository.save(imageFile);

            return imageFile.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ImageFile getImageById(String id) {
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(

                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id))
        );
        return imageFile;
    }

    public void remove(String id) {
        ImageFile imageFile = getImageById(id);

        imageFileRepository.delete(imageFile);
    }

    public List<ImagefileDto> getAllImages() {
        List<ImageFile> imagesFiles = imageFileRepository.findAll();

       return imagesFiles.stream().map(imageFile -> {
            String url = ServletUriComponentsBuilder.
                    fromCurrentContextPath().
                    path("images/download/").
                    path(imageFile.getId()).toUriString();

            return new ImagefileDto(imageFile.getName(), url, imageFile.getType(), imageFile.getLength());

        }).collect(Collectors.toList());


    }
}
