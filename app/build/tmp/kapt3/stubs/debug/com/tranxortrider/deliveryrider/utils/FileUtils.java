package com.tranxortrider.deliveryrider.utils;

/**
 * Utility class for file operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001$B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\u0004J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u0006J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\"2\u0006\u0010\u000e\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\"2\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/FileUtils;", "", "()V", "MAX_FILE_SIZE_BYTES", "", "TAG", "", "calculateInSampleSize", "options", "Landroid/graphics/BitmapFactory$Options;", "reqWidth", "reqHeight", "compressImageFile", "Ljava/io/File;", "file", "maxFileSizeBytes", "copyFileToCache", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "getFileExtension", "fileName", "getFileFromUri", "getFileName", "getFileTypeCategory", "Lcom/tranxortrider/deliveryrider/utils/FileUtils$FileType;", "getMimeType", "extension", "getPathFromUri", "getReadableFileSize", "size", "", "isCompressibleImage", "", "isFileSizeExceeded", "FileType", "app_debug"})
public final class FileUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FileUtils";
    private static final int MAX_FILE_SIZE_BYTES = 5242880;
    @org.jetbrains.annotations.NotNull()
    public static final com.tranxortrider.deliveryrider.utils.FileUtils INSTANCE = null;
    
    private FileUtils() {
        super();
    }
    
    /**
     * Get a File object from a Uri
     */
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getFileFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
        return null;
    }
    
    /**
     * Try to get the actual file path from a content URI
     */
    private final java.lang.String getPathFromUri(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    /**
     * Copy a file from a URI to the app's cache directory
     */
    private final java.io.File copyFileToCache(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    /**
     * Get the file name from a URI
     */
    private final java.lang.String getFileName(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    /**
     * Get the file extension from a file name
     *
     * @param fileName The file name
     * @return The file extension, or an empty string if the file has no extension
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFileExtension(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName) {
        return null;
    }
    
    /**
     * Get the mime type from a file extension
     *
     * @param extension The file extension
     * @return The mime type, or "application/octet-stream" if the mime type could not be determined
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMimeType(@org.jetbrains.annotations.NotNull()
    java.lang.String extension) {
        return null;
    }
    
    /**
     * Check if a file exceeds the maximum size
     *
     * @param file The file to check
     * @return True if the file exceeds the maximum size, false otherwise
     */
    public final boolean isFileSizeExceeded(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return false;
    }
    
    /**
     * Get the file size in a human-readable format
     *
     * @param file The file
     * @return The file size in a human-readable format (e.g., "2.5 MB")
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReadableFileSize(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
    
    /**
     * Get the file size in a human-readable format
     *
     * @param size The file size in bytes
     * @return The file size in a human-readable format (e.g., "2.5 MB")
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReadableFileSize(long size) {
        return null;
    }
    
    /**
     * Compress an image file to reduce its size
     *
     * @param file The image file to compress
     * @param maxFileSizeBytes The maximum file size in bytes
     * @return The compressed file, or the original file if compression failed
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File compressImageFile(@org.jetbrains.annotations.NotNull()
    java.io.File file, int maxFileSizeBytes) {
        return null;
    }
    
    /**
     * Calculate the sample size for image loading
     */
    private final int calculateInSampleSize(android.graphics.BitmapFactory.Options options, int reqWidth, int reqHeight) {
        return 0;
    }
    
    /**
     * Check if a file is a compressible image
     *
     * @param file The file to check
     * @return True if the file is a compressible image, false otherwise
     */
    public final boolean isCompressibleImage(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return false;
    }
    
    /**
     * Get the file type category (image, document, other)
     *
     * @param file The file to check
     * @return The file type category
     */
    @org.jetbrains.annotations.NotNull()
    public final com.tranxortrider.deliveryrider.utils.FileUtils.FileType getFileTypeCategory(@org.jetbrains.annotations.NotNull()
    java.io.File file) {
        return null;
    }
    
    /**
     * Enum for file types
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/tranxortrider/deliveryrider/utils/FileUtils$FileType;", "", "(Ljava/lang/String;I)V", "IMAGE", "DOCUMENT", "OTHER", "app_debug"})
    public static enum FileType {
        /*public static final*/ IMAGE /* = new IMAGE() */,
        /*public static final*/ DOCUMENT /* = new DOCUMENT() */,
        /*public static final*/ OTHER /* = new OTHER() */;
        
        FileType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.tranxortrider.deliveryrider.utils.FileUtils.FileType> getEntries() {
            return null;
        }
    }
}