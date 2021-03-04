package com.shimunmatic.ecommerce.media.controller;

import com.shimunmatic.ecommerce.media.response.ResponseObject;
import com.shimunmatic.ecommerce.media.response.UploadResult;
import com.shimunmatic.ecommerce.media.service.MediaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "api/media/v1/media")
public class MediaUploadController {
    private final MediaStorageService mediaStorageService;

    public MediaUploadController(MediaStorageService mediaStorageService) {
        this.mediaStorageService = mediaStorageService;
    }

    @PostMapping("items/{itemId}")
    public ResponseEntity<ResponseObject<UploadResult>> uploadItemMedia(@PathVariable(name = "itemId") Long itemId, @RequestParam(name = "thumbnail", defaultValue = "false") boolean thumbnail,
                                                                        @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Storing media for item: {}", itemId);
        return ResponseEntity.ok(ResponseObject.ofData(mediaStorageService.storeItemFile(itemId, Objects.requireNonNull(file.getOriginalFilename()), file.getBytes(), thumbnail)));
    }

    @PostMapping("categories/{categoryId}")
    public ResponseEntity<ResponseObject<UploadResult>> uploadCategoryMedia(@PathVariable(name = "categoryId") Long categoryId,
                                                                            @RequestParam(name = "thumbnail", defaultValue = "false") boolean thumbnail,
                                                                            @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Storing media for category: {}", categoryId);
        return ResponseEntity.ok(ResponseObject.ofData(mediaStorageService.storeCategoryFile(categoryId, Objects.requireNonNull(file.getOriginalFilename()), file.getBytes(), thumbnail)));
    }

    @PostMapping("item-variants/{itemVariantId}")
    public ResponseEntity<ResponseObject<UploadResult>> uploadItemVariantMedia(@PathVariable(name = "itemVariantId") Long itemVariantId,
                                                                               @RequestParam(name = "thumbnail", defaultValue = "false") boolean thumbnail,
                                                                               @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Storing media for item variant: {}", itemVariantId);
        return ResponseEntity.ok(ResponseObject.ofData(mediaStorageService.storeItemVariantFile(itemVariantId, Objects.requireNonNull(file.getOriginalFilename()), file.getBytes(), thumbnail)));
    }

}
