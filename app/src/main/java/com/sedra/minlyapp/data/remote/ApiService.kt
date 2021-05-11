package com.sedra.minlyapp.data.remote

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @GET("/player_api.php")
    suspend fun getImageList()

    @POST("/player_api.php")
    @Multipart
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
    )
}