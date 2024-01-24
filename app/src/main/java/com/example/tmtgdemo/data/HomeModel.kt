package com.example.tmtgdemo.data

data class HomeModel (
    val houseId: Int,
    val images: MutableList<Int> = mutableListOf(),
    val description: String,
    val address: String
)