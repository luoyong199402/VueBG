package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiException extends RuntimeException {
    private String code;
    private String message;

    public ApiException(Throwable throwable, String code, String message) {
        super(throwable);
        this.code = code;
        this.message = message;
    }

}
