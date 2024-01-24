package com.example.tmtgdemo.repository

import com.example.tmtgdemo.viewmodel.HouseDetailState

interface HouseDetailRepository {
    suspend fun getHouse(houseId: Int) : HouseDetailState
}