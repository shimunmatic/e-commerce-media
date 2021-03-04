package com.shimunmatic.ecommerce.media.service;

import com.shimunmatic.ecommerce.media.response.UploadResult;
import org.springframework.lang.NonNull;

import java.io.IOException;

public interface MediaStorageService {

    UploadResult storeItemFile(@NonNull Long itemId, @NonNull String originalFileName, byte[] file, boolean thumbnail) throws IOException;

    UploadResult storeCategoryFile(@NonNull Long categoryId, @NonNull String originalFileName, byte[] file, boolean thumbnail) throws IOException;

    UploadResult storeItemVariantFile(@NonNull Long itemVariantFileId, @NonNull String originalFileName, byte[] file, boolean thumbnail) throws IOException;
}
