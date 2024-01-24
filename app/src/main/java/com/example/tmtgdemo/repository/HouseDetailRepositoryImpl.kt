package com.example.tmtgdemo.repository

import com.example.tmtgdemo.data.homesDB
import com.example.tmtgdemo.viewmodel.HouseDetailState
import com.example.tmtgdemo.viewmodel.ListOfHomesState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HouseDetailRepositoryImpl @Inject constructor() : HouseDetailRepository {
    override suspend fun getListOfHouses(): ListOfHomesState {
        return ListOfHomesState.Success(homesDB)
    }

    override suspend fun getHouse(houseId: Int): HouseDetailState {
        return HouseDetailState.Success(house = homesDB.first { homeModel -> homeModel.houseId == houseId })
    }
}