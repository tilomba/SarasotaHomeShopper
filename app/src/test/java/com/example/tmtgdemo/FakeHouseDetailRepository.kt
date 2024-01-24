package com.example.tmtgdemo

import com.example.tmtgdemo.repository.HouseDetailRepository
import com.example.tmtgdemo.viewmodel.HouseDetailState
import com.example.tmtgdemo.viewmodel.ListOfHomesState

class FakeHouseDetailRepository : HouseDetailRepository {
    override suspend fun getListOfHouses(): ListOfHomesState {
        return ListOfHomesState.Success(homesDB = testHomesDB)
    }

    override suspend fun getHouse(houseId: Int): HouseDetailState {
        return HouseDetailState.Success(house = testHomesDB[houseId])
    }

}