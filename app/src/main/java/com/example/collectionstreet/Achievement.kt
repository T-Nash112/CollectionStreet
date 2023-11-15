package com.example.collectionstreet

data class Achievement(
    val name: String,
    var currentCount: Int,
    val targetCount: Int,
    var isAchieved: Boolean
)
