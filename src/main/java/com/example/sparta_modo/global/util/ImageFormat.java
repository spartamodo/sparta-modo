package com.example.sparta_modo.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFormat {
    CARD("/card", new String[]{".jpg", ".png"}),
    BOARD("/board", new String[]{".jpg", "png", ".pdf", ".csv"});

    private final String path;
    private final String[] whiteList;
}
