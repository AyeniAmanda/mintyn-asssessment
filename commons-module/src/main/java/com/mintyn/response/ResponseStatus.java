package com.mintyn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS("SUCCESS"), ERROR("ERROR");

    private String status;
}
