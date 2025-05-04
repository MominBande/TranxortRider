package com.tranxortrider.deliveryrider.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Utility class for file operations
 */
object FileUtils {
    private const val TAG = "FileUtils"
    private const val MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024 // 5MB in bytes
    
    /**
     * Get a File object from a Uri
     */
    fun getFileFromUri(context: Context, uri: Uri): File? {
        try {
            // Try to get file path directly
            val path = getPathFromUri(context, uri)
            if (path != null) {
                return File(path)
            }
            
            // If path retrieval failed, copy the file to app's cache directory
            return copyFileToCache(context, uri)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting file from URI: ${e.message}")
            return null
        }
    }
    
    /**
     * Try to get the actual file path from a content URI
     */
    private fun getPathFromUri(context: Context, uri: Uri): String? {
        // Handle file:// URIs
        if ("file" == uri.scheme) {
            return uri.path
        }
        
        // Handle content:// URIs
        if ("content" == uri.scheme) {
            var cursor: Cursor? = null
            try {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(uri, projection, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    return cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting path from URI: ${e.message}")
            } finally {
                cursor?.close()
            }
        }
        
        return null
    }
    
    /**
     * Copy a file from a URI to the app's cache directory
     */
    private fun copyFileToCache(context: Context, uri: Uri): File? {
        var inputStream: FileInputStream? = null
        var outputStream: FileOutputStream? = null
        
        try {
            val fileName = getFileName(context, uri)
            if (fileName.isNullOrEmpty()) {
                return null
            }
            
            val cacheDir = context.cacheDir
            val file = File(cacheDir, fileName)
            
            val contentResolver = context.contentResolver
            inputStream = contentResolver.openInputStream(uri) as? FileInputStream
            outputStream = FileOutputStream(file)
            
            if (inputStream != null) {
                val buffer = ByteArray(4 * 1024) // 4KB buffer
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
                return file
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error copying file to cache: ${e.message}")
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                Log.e(TAG, "Error closing streams: ${e.message}")
            }
        }
        
        return null
    }
    
    /**
     * Get the file name from a URI
     */
    private fun getFileName(context: Context, uri: Uri): String? {
        var fileName: String? = null
        
        // Try to get file name from content resolver query
        if ("content" == uri.scheme) {
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        fileName = cursor.getString(nameIndex)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting file name: ${e.message}")
            } finally {
                cursor?.close()
            }
        }
        
        // If we couldn't get the name, use the last path segment
        if (fileName == null) {
            fileName = uri.lastPathSegment
        }
        
        // If still no file name, create a timestamp-based name
        if (fileName == null) {
            fileName = "document_${System.currentTimeMillis()}.jpg"
        }
        
        return fileName
    }
    
    /**
     * Get the file extension from a file name
     * 
     * @param fileName The file name
     * @return The file extension, or an empty string if the file has no extension
     */
    fun getFileExtension(fileName: String): String {
        return if (fileName.contains(".")) {
            fileName.substring(fileName.lastIndexOf(".") + 1)
        } else {
            ""
        }
    }
    
    /**
     * Get the mime type from a file extension
     * 
     * @param extension The file extension
     * @return The mime type, or "application/octet-stream" if the mime type could not be determined
     */
    fun getMimeType(extension: String): String {
        return when (extension.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            else -> "application/octet-stream"
        }
    }
    
    /**
     * Check if a file exceeds the maximum size
     * 
     * @param file The file to check
     * @return True if the file exceeds the maximum size, false otherwise
     */
    fun isFileSizeExceeded(file: File): Boolean {
        return file.length() > MAX_FILE_SIZE_BYTES
    }
    
    /**
     * Get the file size in a human-readable format
     * 
     * @param file The file
     * @return The file size in a human-readable format (e.g., "2.5 MB")
     */
    fun getReadableFileSize(file: File): String {
        val size = file.length()
        return getReadableFileSize(size)
    }
    
    /**
     * Get the file size in a human-readable format
     * 
     * @param size The file size in bytes
     * @return The file size in a human-readable format (e.g., "2.5 MB")
     */
    fun getReadableFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        
        return String.format("%.1f %s", size / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
    }
    
    /**
     * Compress an image file to reduce its size
     * 
     * @param file The image file to compress
     * @param maxFileSizeBytes The maximum file size in bytes
     * @return The compressed file, or the original file if compression failed
     */
    fun compressImageFile(file: File, maxFileSizeBytes: Int = MAX_FILE_SIZE_BYTES): File {
        if (file.length() <= maxFileSizeBytes) {
            return file // No need to compress
        }
        
        try {
            // Load the bitmap
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(file.absolutePath, options)
            
            // Calculate sample size
            options.inSampleSize = calculateInSampleSize(options, 1024, 1024)
            options.inJustDecodeBounds = false
            
            // Decode bitmap with inSampleSize set
            val bitmap = BitmapFactory.decodeFile(file.absolutePath, options) ?: return file
            
            // Start with high quality (90)
            var quality = 90
            var compressedBytes: ByteArray
            
            do {
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                compressedBytes = outputStream.toByteArray()
                
                // Reduce quality by 10% each iteration
                quality -= 10
            } while (compressedBytes.size > maxFileSizeBytes && quality > 10)
            
            // Create a new file with the compressed bitmap
            val compressedFile = File(file.parentFile, "compressed_${file.name}")
            FileOutputStream(compressedFile).use { fos ->
                fos.write(compressedBytes)
            }
            
            Log.d(TAG, "Compressed image from ${getReadableFileSize(file.length())} to ${getReadableFileSize(compressedFile.length())}")
            
            return compressedFile
        } catch (e: Exception) {
            Log.e(TAG, "Error compressing image", e)
            return file // Return original file if compression fails
        }
    }
    
    /**
     * Calculate the sample size for image loading
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        
        return inSampleSize
    }
    
    /**
     * Check if a file is a compressible image
     * 
     * @param file The file to check
     * @return True if the file is a compressible image, false otherwise
     */
    fun isCompressibleImage(file: File): Boolean {
        val extension = getFileExtension(file.name).lowercase()
        return extension == "jpg" || extension == "jpeg" || extension == "png"
    }
    
    /**
     * Get the file type category (image, document, other)
     * 
     * @param file The file to check
     * @return The file type category
     */
    fun getFileTypeCategory(file: File): FileType {
        val extension = getFileExtension(file.name).lowercase()
        return when (extension) {
            "jpg", "jpeg", "png", "gif", "bmp" -> FileType.IMAGE
            "pdf", "doc", "docx", "txt", "rtf" -> FileType.DOCUMENT
            else -> FileType.OTHER
        }
    }
    
    /**
     * Enum for file types
     */
    enum class FileType {
        IMAGE,
        DOCUMENT,
        OTHER
    }
} 