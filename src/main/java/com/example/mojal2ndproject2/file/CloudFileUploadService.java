package com.example.mojal2ndproject2.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudFileUploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final AmazonS3 amazonS3;

    public CloudFileUploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    //파일 이름에 날짜 붙여주기
    public String makeFolder(String rootType) { //rootType -> 폴더 구분을 위한 변수
        String folder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = rootType + "/" + folder;

//        File uploadPathFolder = new File(folderPath);
//        if (!uploadPathFolder.exists()) {
//            uploadPathFolder.mkdirs();
//        }

        return folderPath;
    }

    public List<String> uploadImages(String rootType , MultipartFile[] files) {
        List<String> images = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            //폴더 경로 지정
            String folderPath = makeFolder(rootType);

            //경로 + 난수 + getOriginalFilename
            String fileName = folderPath + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

            try {
                amazonS3.putObject(bucketName, fileName, multipartFile.getInputStream(), metadata);
                images.add("https://mojal.s3.ap-northeast-2.amazonaws.com/"+fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return images;
    }

    public String uploadImage(String rootType , MultipartFile file) {
        String image = "";

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        //폴더 경로 지정
        String folderPath = makeFolder(rootType);

        //경로 + 난수 + getOriginalFilename
        String fileName = folderPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
            image="https://mojal.s3.ap-northeast-2.amazonaws.com/"+bucketName+"/"+fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }
}
