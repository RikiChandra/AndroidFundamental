package com.example.githubapp.view.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.UserGithub
import com.example.githubapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val apiService = ApiConfig().apiService

    private val _userDetail = MutableLiveData<UserGithub>()
    val userDetail: LiveData<UserGithub> = _userDetail

    private lateinit var context: Context

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getUserDetail(username: String) {
        _isLoading.postValue(true)
        val client = apiService.getUserDetail(username)
        client.enqueue(object : Callback<UserGithub> {
            override fun onResponse(call: Call<UserGithub>, response: Response<UserGithub>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.postValue(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithub>, t: Throwable) {
                _isLoading.postValue(false)
                // handle error
                val errorMessage = "Error: ${t.message}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e(TAG, errorMessage)
            }
        })
    }

    companion object {
        private const val TAG = "DetailModelView"
    }
}

