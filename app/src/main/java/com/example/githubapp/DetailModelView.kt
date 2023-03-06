package com.example.githubapp

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModelView : ViewModel() {

    private val apiService = ApiConfig().apiService

    private val _userDetail = MutableLiveData<UserGithub>()
    val userDetail: LiveData<UserGithub> = _userDetail

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
                        val users = responseBody
                        _userDetail.postValue(users)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithub>, t: Throwable) {
                _isLoading.postValue(false)
                // handle error
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailModelView"
    }
}

