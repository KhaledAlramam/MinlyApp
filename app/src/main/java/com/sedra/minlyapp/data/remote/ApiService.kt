package com.sedra.minlyapp.data.remote

import com.sedra.minlyapp.data.model.GetImageResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    companion object {
        const val BASE_URL = "https://12cd05b04702.ngrok.io/"
    }

    @GET("/images")
    suspend fun getImageList(): GetImageResponse

    @POST("/images")
    @Multipart
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    )
}