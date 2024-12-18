package com.icdominguez.icdominguez.memecreator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import com.icdominguez.icdominguez.memecreator.presentation.model.MemeBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random

class FileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val MEMES_FOLDER = File(context.filesDir, "memes")

    fun saveBitmapToFile(imageBitmap: ImageBitmap): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        val bitmap = imageBitmap.asAndroidBitmap()

        if(!MEMES_FOLDER.exists()) {
            MEMES_FOLDER.mkdirs()
        }

        val imageFile = File(MEMES_FOLDER, "$formatted.png")
        try {
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            return imageFile.path
        } catch (e: Exception) {
            Log.e("icd", "saveBitmapImage: ", e)
            return null
        }
    }

    fun removeFile(path: String) {
        try {
            val file = File(path)
            file.delete()
        } catch (e: Exception) {
            Log.e("icd", "There was an error trying to delete meme: ${e.message}")
        }
    }

    fun createImageBitmap(file: File): ImageBitmap? {
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

    fun saveImageToCache(imageBitmap: ImageBitmap): Uri? {
        return try {
            val cachePath = File(context.cacheDir, "memes")
            val bitmap = imageBitmap.asAndroidBitmap()
            if (!cachePath.exists()) {
                cachePath.mkdirs()
            }
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val formatted = current.format(formatter)
            val imageFile = File(cachePath, "${Random.nextInt()}.png")
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