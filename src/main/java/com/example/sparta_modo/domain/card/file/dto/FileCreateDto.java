package com.example.sparta_modo.domain.card.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Getter
public class FileCreateDto {

    private List<MultipartFile> file;
}
