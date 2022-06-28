package com.taufik.androidintemediate.media.cameraxgalleryupload.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taufik.androidintemediate.media.cameraxgalleryupload.api.ApiConfig
import com.taufik.androidintemediate.media.cameraxgalleryupload.response.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraXViewModel : ViewModel() {

    private val apiConfig = ApiConfig.getApiService()

    private val _uploadImage = MutableLiveData<FileUploadResponse>()
    val uploadImage: LiveData<FileUploadResponse> = _uploadImage

    private val _isUploadSuccess = MutableLiveData<Boolean>()
    val isUploadSuccess = _isUploadSuccess

    fun uploadImage(file: MultipartBody.Part, description: RequestBody) {
        apiConfig.uploadImage(file, description)
            .enqueue(object : Callback<FileUploadResponse>{
                override fun onResponse(
                    call: Call<FileUploadResponse>,
                    response: Response<FileUploadResponse>
                ) {
                    if (response.isSuccessful) {
                        _uploadImage.value = response.body()
                        _isUploadSuccess.value = true
                    }
                }

                override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                    _isUploadSuccess.value = false
                    Log.e(TAG, "onFailure: ${t.printStackTrace()}")
                }
            })
    }

    companion object {
        private val TAG = CameraXViewModel::class.java.simpleName
    }
}