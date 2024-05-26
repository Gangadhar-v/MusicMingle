package com.example.musicmingle.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicmingle.repo.MusicRepository

class MusicViewModelFactory(private val repo:MusicRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return  MusicViewModel(repo) as T
    }
}