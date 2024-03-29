package com.example.githubapp.view.detail.follows


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.UserGithub
import com.example.githubapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowViewModel : ViewModel() {

    private val apiService = ApiConfig().apiService

    private val _users = MutableLiveData<List<UserGithub>>()
    val users: LiveData<List<UserGithub>> = _users


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    internal fun getFollowers(username: String) {
        _isLoading.postValue(true)
        val client = apiService.getUserFollowers(username)
        client.enqueue(object : Callback<List<UserGithub>> {
            override fun onResponse(call: Call<List<UserGithub>>, response: Response<List<UserGithub>>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _users.postValue(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithub>>, t: Throwable) {
                _isLoading.postValue(false)
                // handle error
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    internal fun getFollowing(username: String) {
        _isLoading.postValue(true)
        val client = apiService.getUserFollowing(username)
        client.enqueue(object : Callback<List<UserGithub>> {
            override fun onResponse(call: Call<List<UserGithub>>, response: Response<List<UserGithub>>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _users.postValue(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithub>>, t: Throwable) {
                _isLoading.postValue(false)
                // handle error
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }

}
