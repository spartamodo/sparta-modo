package com.example.sparta_modo.domain.card.file.dto;

import com.example.sparta_modo.global.entity.File;
import lombok.Getter;

import java.util.List;

@Getter
public class FileFindDto {
    private Long cardId;
    private List<File> fileList;

    public FileFindDto(Long cardId, List<File> fileList) {
        this.cardId = cardId;
        this.fileList = fileList;
    }
}
