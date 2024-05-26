package com.example.musicmingle.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicmingle.model.MusicData
import com.example.musicmingle.repo.MusicRepository
import kotlinx.coroutines.launch

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _musicData = MutableLiveData<MusicData>()
    val musicData: LiveData<MusicData> get() = _musicData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchMusicData(artist: String) {
        viewModelScope.launch {
            val sanitizedArtist = artist.trim().replace("\\s+".toRegex(), " ")
            val result = repository.getMusicData(sanitizedArtist)
            result.fold(
                onSuccess = { _musicData.postValue(it) },
                onFailure = { _error.postValue(it.message) }
            )
        }
    }
}

