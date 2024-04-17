package com.manpro.wibufinders.remote.network.network

import com.manpro.wibufinders.remote.network.response.BiodataResponse
import com.manpro.wibufinders.remote.network.response.CreateEventResponse
import com.manpro.wibufinders.remote.network.response.EventResponse
import com.manpro.wibufinders.remote.network.response.LoginResponse
import com.manpro.wibufinders.remote.network.response.RegisterResponse
import com.manpro.wibufinders.request.CreateEventRequest
import com.manpro.wibufinders.request.LoginRequest
import com.manpro.wibufinders.request.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Headers



interface ApiService {
    @POST("/register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @POST("/login")
    suspend fun login(@Body body: LoginRequest) : LoginResponse

    @GET("/me")
    suspend fun getBiodata(@Header("ApiKey") apiKey: String): BiodataResponse

    @GET("/events")
    suspend fun getEvent(): EventResponse
    
    @Headers("Accept: application/json")
    @POST("/createEvent")
    suspend fun createEvent(@Body body : CreateEventRequest) : CreateEventResponse

}
