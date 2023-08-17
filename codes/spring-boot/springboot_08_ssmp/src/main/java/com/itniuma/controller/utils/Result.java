package com.itniuma.controller.utils;

import lombok.Data;

@Data
public class Result {
    private Boolean code;
    private Object data;

    private String message;

    public Result() {
    }

    public Result(Boolean code) {
        this.code = code;
    }

    public Result(Boolean code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Boolean code, String message) {
        this.code = code;
        this.message = message;
    }
}
