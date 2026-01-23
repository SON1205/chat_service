package com.yeahpeu.backend.auth.v2.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DeviceType {
    PC("PC"),
    MOBILE("모바일"),
    TABLET("태블릿");

    private final String description;
}
