package com.example.tmtgdemo.repository

import com.example.tmtgdemo.data.homesDB
import com.example.tmtgdemo.viewmodel.HouseDetailState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HouseDetailRepositoryImpl @Inject constructor() : HouseDetailRepository {
    override suspend fun getHouse(houseId: Int): HouseDetailState {
        return HouseDetailState.Success(house = homesDB.first { homeModel -> homeModel.homeId == houseId })
    }
}