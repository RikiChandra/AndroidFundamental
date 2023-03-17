package com.example.githubapp.view.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val settingPreference: SettingPreference) : ViewModel() {
    fun getSettingTheme(): LiveData<Boolean> {
        return settingPreference.getTheme().asLiveData()
    }

    fun saveSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreference.setTheme(isDarkModeActive)
        }
    }




}