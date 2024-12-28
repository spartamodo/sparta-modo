package com.example.sparta_modo.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFormat {
    CARD("/card", new String[]{".jpg", ".png", ".pdf", ".csv"}),
    BOARD("/board", new String[]{".jpg", "png"});

    private final String path;
    private final String[] whiteList;
}
