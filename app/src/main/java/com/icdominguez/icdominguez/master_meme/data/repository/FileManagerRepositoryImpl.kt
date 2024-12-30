package com.icdominguez.icdominguez.master_meme.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FileManagerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): FileManagerRepository {

    private companion object {
        private const val MEMES_FOLDER = "memes"
    }

    override suspend fun saveImageToFile(
        imageBitmap: ImageBitmap,
        inCache: Boolean
    ): String? =
        withContext(Dispatchers.IO) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val formatted = current.format(formatter)

            val folder = File(context.filesDir, "memes")

            if(!folder.exists()) {
                folder.mkdirs()
            }

            val imageFile = File(folder, "${formatted}.png")
            try {
                val outputStream = FileOutputStream(imageFile)
                imageBitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                imageFile.path
            } catch (e: Exception) {
                Log.e("icd", "saveBitmapImage: ", e)
                null
            }
        }

    override fun removeFile(path: String) {
        try {
            val file = File(path)
            file.delete()
        } catch (e: Exception) {
            Log.e("icd", "There was an error trying to delete meme: ${e.message}")
        }
    }

    override fun cleanCache() {
        val folder = File(context.cacheDir, MEMES_FOLDER)

        if(folder.exists()) {
            folder.listFiles()?.forEach {
                removeFile(it.path)
            }
        }
    }

    override fun createImageBitmap(file: File): ImageBitmap? {
        var imageBitmap: ImageBitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            imageBitmap = BitmapFactory.decodeStream(FileInputStream(file) , null, options)?.asImageBitmap()
        } catch (e: Exception) {
            Log.e("icd", "Error while converting file to image bitmap")
        }
        return imageBitmap
    }

    override fun saveImageToCache(imageBitmap: ImageBitmap): Uri? {
        return try {
            val cachePath = File(context.cacheDir, "memes")
            val bitmap = imageBitmap.asAndroidBitmap()
            if (!cachePath.exists()) {
                cachePath.mkdirs()
            }
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val formatted = current.format(formatter)
            val imageFile = File(cachePath, "${formatted}.png")
            FileOutputStream(imageFile).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
        } catch (e: Exception) {
            Log.e("icd", "saveBitmapImage: ", e)
            null
        }
    }

}