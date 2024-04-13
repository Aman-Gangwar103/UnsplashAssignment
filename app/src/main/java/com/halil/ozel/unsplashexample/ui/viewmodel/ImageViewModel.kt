package com.halil.ozel.unsplashexample.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halil.ozel.unsplashexample.model.ImageItem
import com.halil.ozel.unsplashexample.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    private val _response = MutableLiveData<List<ImageItem>>()
    val responseImages: LiveData<List<ImageItem>> get() = _response

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        getAllImages()
    }

    private fun getAllImages() = viewModelScope.launch {
        repository.getAllImages().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            }

            else {
                _errorMessage.postValue("Failed to fetch images: ${response.code()}")
            }
        }
    }
}
