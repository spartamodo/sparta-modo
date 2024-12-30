package com.example.sparta_modo.domain.card.file.dto;

import com.example.sparta_modo.global.entity.File;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FileFindDto {
    private Long cardId;
    private List<String> fileUrls;

    public FileFindDto(Long cardId, List<File> fileList) {
        this.cardId = cardId;
        this.fileUrls = fileList.stream()
                .map(File::getUrl) // File 엔터티의 URL만 반환
                .collect(Collectors.toList());
    }
}
