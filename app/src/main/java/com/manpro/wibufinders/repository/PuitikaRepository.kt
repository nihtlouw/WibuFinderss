package com.manpro.wibufinders.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.manpro.wibufinders.pref.UserModel
import com.manpro.wibufinders.pref.UserPreference
import com.manpro.wibufinders.remote.network.network.ApiConfig
import com.manpro.wibufinders.remote.network.network.ApiService
import com.manpro.wibufinders.remote.network.response.CreateEventResponse
import com.manpro.wibufinders.remote.network.response.EventResponse
import com.manpro.wibufinders.remote.network.response.LoginResponse
import com.manpro.wibufinders.remote.network.response.RegisterResponse
import com.manpro.wibufinders.request.CreateEventRequest
import com.manpro.wibufinders.request.LoginRequest
import com.manpro.wibufinders.request.RegisterRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.lang.Exception

class PuitikaRepository(
    private val pref: UserPreference,
    private var apiService: ApiService
) {

    fun register(body: RegisterRequest): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiService = ApiConfig.getApiService()
            val res = apiService.register(body)
            if (res.status == "success") emit(Result.Success(res))
            else emit(Result.Error(res.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(body: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiService = ApiConfig.getApiService()
            val loginRes = apiService.login(body)
            if (loginRes.status == "success") {
                try {
                    val bioRes = apiService.getBiodata(loginRes.apikey)
                    pref.saveSession(UserModel(email = bioRes.email, username = bioRes.username, api = loginRes.apikey))
                }catch (e:Exception) {
                    Log.e("/me", e.message.toString())
                }
                emit(Result.Success(loginRes))
            }
            else emit(Result.Error(loginRes.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getEvents(): LiveData<Result<EventResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiService = ApiConfig.getApiService()
            val res = apiService.getEvent()
            if(!res.error) emit(Result.Success(res))
            else emit(Result.Error(res.status))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun createEvent(body: CreateEventRequest): LiveData<Result<CreateEventResponse>> = liveData {
        emit(Result.Loading)
        try {
            apiService = ApiConfig.getApiService()
            val res = apiService.createEvent(body)
            if (res.status == "success") emit(Result.Success(res))
            else emit(Result.Error(res.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: PuitikaRepository? = null
        fun getInstance(
            pref: UserPreference,
            apiService: ApiService
        ): PuitikaRepository =
            instance ?: synchronized(this) {
                instance ?: PuitikaRepository(pref, apiService)
            }.also { instance = it }
    }
}

