package com.example.tmtgdemo.repository

import com.example.tmtgdemo.viewmodel.HouseDetailState
import com.example.tmtgdemo.viewmodel.ListOfHomesState

interface HouseDetailRepository {
    suspend fun getListOfHouses() : ListOfHomesState
    suspend fun getHouse(houseId: Int) : HouseDetailState
}