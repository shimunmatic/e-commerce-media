package com.shimunmatic.ecommerce.media.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResult {
    private String path;
    private String fileName;
}
