package com.example.tmtgdemo.data

data class HomeModel (
    val homeId: Int,
    val images: MutableList<Int> = mutableListOf<Int>(),
    val description: String,
    val address: String
)