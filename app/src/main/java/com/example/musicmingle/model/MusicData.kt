package com.example.musicmingle.model

data class MusicData(
    val `data`: List<Data>,
    val next: String,
    val total: Int
)