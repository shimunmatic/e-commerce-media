package com.shimunmatic.ecommerce.media.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseObject<T> {
    private T data;
    private String errorMessage;
    private boolean success;
    private Instant timestamp;

    public ResponseObject(T data, String errorMessage, boolean success) {
        this.data = data;
        this.errorMessage = errorMessage;
        this.success = success;
        this.timestamp = Instant.now();
    }

    public static <T> ResponseObject<T> ofData(T data) {
        return new ResponseObject<>(data, null, true);
    }

    public static <T> ResponseObject<T> ofErrorMessage(String errorMessage) {
        return new ResponseObject<>(null, errorMessage, false);
    }

    public static <T> ResponseObject<T> ofErrorMessage(T data, String errorMessage) {
        return new ResponseObject<>(data, errorMessage, false);
    }

}