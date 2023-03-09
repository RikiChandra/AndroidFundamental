package com.example.githubapp.view.main


import android.util.Log
import retrofit2.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.UserGithub
import com.example.githubapp.UserResponse
import com.example.githubapp.api.ApiConfig
import retrofit2.Response
import retrofit2.Callback


class MainViewModel : ViewModel() {

    private val apiService = ApiConfig().apiService

    private val _users = MutableLiveData<List<UserGithub>>()
    val users: LiveData<List<UserGithub>>  = _users
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _isNotFound = MutableLiveData<Boolean>()
    val isNotFound: LiveData<Boolean> = _isNotFound


    internal fun getUserSearch(query: String) {
        _isLoading.postValue(true)
        val client = apiService.getUserSearch(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val userList = responseBody.git
                        _users.postValue(userList)
                        _totalCount.postValue(responseBody.totalCount)
                        _isNotFound.postValue(userList.isEmpty())
                    }
                    else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.postValue(false)
                // handle error
                Log.e("MainViewModel", "Error: ${t.message}")
                _isLoading.value = false
            }
        })

    }

    companion object {
        private const val TAG = "MainViewModel"
    }






}