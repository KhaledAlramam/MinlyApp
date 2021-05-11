package com.sedra.minlyapp.data

import com.sedra.minlyapp.data.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DataRepository @Inject constructor(
    val service: ApiService
) {


    suspend fun uploadImage(image: MultipartBody.Part) =
        service.uploadImage(image)

    suspend fun getImageList() =
        service.getImageList()

}