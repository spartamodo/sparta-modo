package com.example.sparta_modo.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.sparta_modo.domain.board.dto.BoardDto;
import com.example.sparta_modo.domain.card.dto.CardDto;
import com.example.sparta_modo.global.exception.ImageException;
import com.example.sparta_modo.global.exception.errorcode.ImageErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.sparta_modo.global.util.ImageFormat.CARD;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public void uploadImage(BoardDto.Request boardDto, Long boardId) throws IOException {
        saveFileToS3(boardDto.getImage(), ImageFormat.BOARD, boardId);

    }

    @Override
    public void uploadFiles(CardDto.Request cardDto, Long cardId) throws IOException {
        for (MultipartFile file : cardDto.getImages()) {
            saveFileToS3(file, CARD, cardId);
        }
    }

    private void saveFileToS3(MultipartFile file, ImageFormat imageFormat, Long id) throws IOException {

        validateFileCase(file, imageFormat);

        StringBuilder basePackageName = new StringBuilder(bucket).append(imageFormat.name());
        String s3FileName = file.getOriginalFilename();

        if (imageFormat == CARD) {
            basePackageName.append("/").append(id);
        }

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(file.getInputStream().available());

        amazonS3.putObject(basePackageName.toString(), s3FileName, file.getInputStream(), objMeta);
    }

    private void validateFileCase(MultipartFile file, ImageFormat imageFormat) {
        String fileExtension = getFileExtensionWithDot(file.getOriginalFilename());

        if (isWhiteList(fileExtension, imageFormat.getWhiteList())) {
            throw new ImageException(ImageErrorCode.NOT_ALLOW_FILE_EXTENSION);
        }
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
