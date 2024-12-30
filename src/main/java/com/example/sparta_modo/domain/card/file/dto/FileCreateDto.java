package com.example.sparta_modo.domain.card.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class FileCreateDto {

    private MultipartFile[] file;
}
