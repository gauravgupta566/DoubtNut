package com.example.doubtnut.responsemodel

data class HeadlineModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)