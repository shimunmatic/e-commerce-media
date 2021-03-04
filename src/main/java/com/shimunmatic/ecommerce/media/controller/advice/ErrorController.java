package com.shimunmatic.ecommerce.media.controller.advice;

import com.shimunmatic.ecommerce.media.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<ResponseObject<?>> ioError(IOException e) {
        return ResponseEntity.badRequest().body(ResponseObject.ofErrorMessage(e.getLocalizedMessage()));
    }
}
