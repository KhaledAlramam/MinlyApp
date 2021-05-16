package com.sedra.minlyapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sedra.minlyapp.data.DataRepository
import com.sedra.minlyapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DataRepository,
): ViewModel() {


    fun uploadImage(imagePath: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val file = File(imagePath)
            val requestBody: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val fileToUpload: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", file.name, requestBody)
            emit(Resource.success(data = repository.uploadImage(fileToUpload)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message.toString()))
        }
    }

    fun getImageList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getImageList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message.toString()))
        }
    }

}