package com.example.sparta_modo.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.domain.card.file.dto.FileCreateDto;
import com.example.sparta_modo.global.exception.CommonException;
import com.example.sparta_modo.global.exception.ImageException;
import com.example.sparta_modo.global.exception.errorcode.ErrorCode;
import com.example.sparta_modo.global.exception.errorcode.ImageErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.sparta_modo.global.util.ImageFormat.CARD;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String BUCKET;

    @Override
    public String uploadImage(BoardDto.Request boardDto, Long boardId) throws IOException {

        if(boardDto.getImage().length != 1) {
            throw new ImageException(ImageErrorCode.TOO_MANY_FILES);
        }
        return saveFileToS3(boardDto.getImage()[0], ImageFormat.BOARD, boardId);
    }

    @Override
    public List<String> uploadFiles(FileCreateDto dto, Long cardId) throws IOException {

        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : dto.getFile()) {
            String fileUrl = saveFileToS3(file, CARD, cardId);
            uploadedUrls.add(fileUrl);
        }
        return uploadedUrls;
    }

    private String saveFileToS3(MultipartFile file, ImageFormat imageFormat, Long id) throws IOException {

        if (file == null) {
            throw new ImageException(ImageErrorCode.NO_FILE);
        }

        String fileExtension = getFileExtensionWithDot(file.getOriginalFilename());
        validateFileExtension(fileExtension, imageFormat);

        StringBuilder basePackageName = new StringBuilder(BUCKET);

        StringBuilder secondPackageName = new StringBuilder(imageFormat.getPath());


        String s3FileName = String.valueOf(id);

        if (imageFormat == CARD) {
            secondPackageName.append("/").append(id);
            s3FileName = getOriginalFileNameWithOutDot(file.getOriginalFilename());
        }
        String fileUrl = s3FileName + fileExtension;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(file.getInputStream().available());

        amazonS3.putObject(basePackageName.append(secondPackageName).toString(), fileUrl, file.getInputStream(), objMeta);
        return getPublicUrl(secondPackageName + "/" + fileUrl);
    }

    private String getOriginalFileNameWithOutDot(String originalFilename) {
        int dotIndex = originalFilename.lastIndexOf(".");

        if (dotIndex == -1) {
            throw new CommonException(ErrorCode.BAD_REQUEST, "파일 확장자가 없습니다.");
        }
        return originalFilename.substring(0, dotIndex);
    }

    private void validateFileExtension(String fileExtension, ImageFormat imageFormat) {
        if (isWhiteList(fileExtension, imageFormat.getWhiteList())) {
            throw new ImageException(ImageErrorCode.NOT_ALLOW_FILE_EXTENSION);
        }
    }

    private String getPublicUrl(String fileLocation) {
        return String.format("https://%s.s3.%s.amazonaws.com%s", BUCKET, amazonS3.getRegionName(), fileLocation);
    }

    private boolean isWhiteList(String fileExtension, String[] whiteList) {
        return !PatternMatchUtils.simpleMatch(whiteList, fileExtension);
    }

    private String getFileExtensionWithDot(String originalFileName) {
        int lastIndexOfDot = originalFileName.lastIndexOf('.');

        if (lastIndexOfDot == -1) {
            throw new ImageException(ImageErrorCode.NO_EXTENSION_FILE); // 확장자가 없는 경우
        }

        return originalFileName.substring(lastIndexOfDot);
    }


}
