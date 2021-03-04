package com.shimunmatic.ecommerce.media.service;

import com.shimunmatic.ecommerce.media.response.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class MediaStorageServiceImpl implements MediaStorageService {
    private final int THUMBNAIL_WIDTH;
    private final int THUMBNAIL_HEIGHT;
    private final String CATEGORY_DATA_FOLDER;
    private final String ITEM_DATA_FOLDER;
    private final String ITEM_VARIANT_DATA_FOLDER;

    public MediaStorageServiceImpl(@Value("${media.thumbnail.width}") int thumbnailWidth, @Value("${media.thumbnail.height}") int thumbnailHeight,
                                   @Value("${media.data-folder.category}") String categoryDataFolder, @Value("${media.data-folder.item}") String itemDataFolder,
                                   @Value("${media.data-folder.item-variant}") String itemVariantDataFolder) {
        THUMBNAIL_WIDTH = thumbnailWidth;
        THUMBNAIL_HEIGHT = thumbnailHeight;
        CATEGORY_DATA_FOLDER = categoryDataFolder;
        ITEM_DATA_FOLDER = itemDataFolder;
        ITEM_VARIANT_DATA_FOLDER = itemVariantDataFolder;
    }

    @Override
    public UploadResult storeItemFile(Long itemId, String originalFileName, byte[] file, boolean thumbnail) throws IOException {
        Path itemFolder = getPathForMediaId(itemId, ITEM_DATA_FOLDER, thumbnail);
        return storeMediaFile(itemFolder, originalFileName, file, thumbnail);
    }

    @Override
    public UploadResult storeCategoryFile(Long categoryId, String originalFileName, byte[] file, boolean thumbnail) throws IOException {
        Path itemFolder = getPathForMediaId(categoryId, CATEGORY_DATA_FOLDER, thumbnail);
        return storeMediaFile(itemFolder, originalFileName, file, thumbnail);
    }

    @Override
    public UploadResult storeItemVariantFile(Long itemVariantId, String originalFileName, byte[] file, boolean thumbnail) throws IOException {
        Path itemFolder = getPathForMediaId(itemVariantId, ITEM_VARIANT_DATA_FOLDER, thumbnail);
        return storeMediaFile(itemFolder, originalFileName, file, thumbnail);
    }

    private Path getPathForMediaId(@NonNull Long mediaId, String mediaRootPath, boolean thumbnail) {
        long parentFolderNumber = mediaId / 100;
        return thumbnail ? Paths.get(mediaRootPath, String.valueOf(parentFolderNumber), String.valueOf(mediaId), "thumbnail")
                : Paths.get(mediaRootPath, String.valueOf(parentFolderNumber), String.valueOf(mediaId));
    }

    private UploadResult storeMediaFile(Path parentPath, String originalFileName, byte[] file, boolean thumbnail) throws IOException {
        String[] split = originalFileName.split("\\.");
        String fileName = String.format("%s.%s", UUID.randomUUID().toString(), split[1]);
        Path filePath = Path.of(parentPath.toString(), fileName);
        Files.createDirectories(parentPath);
        log.info("Storing file: {} to path: {}", originalFileName, filePath);
        if (thumbnail) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizeImage(file, THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT), split[1], outputStream);
            file = outputStream.toByteArray();
        }
        Files.write(filePath, file);
        return UploadResult.builder().path(filePath.toString()).fileName(fileName).build();
    }

    BufferedImage resizeImage(byte[] file, int targetWidth, int targetHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file));
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
