package com.example.mojal2ndproject2.file;

import com.example.mojal2ndproject2.common.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CloudFileUploadController {
    private final CloudFileUploadService cloudFileUploadService;
    @RequestMapping(method = RequestMethod.POST, value = "/upload/image")
    public BaseResponse<List<String>> uploadImage(@RequestPart MultipartFile[] files, @RequestPart String postType){
        List<String> images = cloudFileUploadService.uploadImages(postType,files);
        System.out.println(images);
        return new BaseResponse<>(images);
    }
}
