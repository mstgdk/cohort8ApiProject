package com.patika.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageSavedResponse extends PatikaResponse{
    private String imageId;

    public ImageSavedResponse(String message, Boolean success, String imageId) {
        super(message, success);
        this.imageId = imageId;
    }
}
