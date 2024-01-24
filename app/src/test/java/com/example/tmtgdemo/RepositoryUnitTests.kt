package com.example.tmtgdemo

import com.example.tmtgdemo.data.HomeModel
import com.example.tmtgdemo.viewmodel.HouseDetailState
import com.example.tmtgdemo.viewmodel.ListOfHomesState
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class RepositoryUnitTests {
    @Test
    fun house_repository_get_all_houses() = runTest {
        val testHomes = FakeHouseDetailRepository().getListOfHouses() as ListOfHomesState.Success

        Assert.assertEquals(testHomes.homesDB.size, testHomesDB.size)
    }

    @Test
    fun house_repository_get_single_house() = runTest {
        val testSingleHouse = FakeHouseDetailRepository().getHouse(1) as HouseDetailState.Success

        Assert.assertNotNull(testSingleHouse.house)
    }
}

val testHomesDB = listOf(
    HomeModel(
        1,
        mutableListOf(
            R.drawable.house1_tn,
            R.drawable.house1a,
            R.drawable.house1b,
            R.drawable.house1c
        ),
        "Economical",
        address = "1300 Grand Blvd #201, SARASOTA, FL 34232"
    ),
    HomeModel(
        2,
        mutableListOf(
            R.drawable.house2_tn,
            R.drawable.house2a,
            R.drawable.house2b,
            R.drawable.house2c
        ),
        "Luxury",
        address = "3369 Ramblewood Pl, SARASOTA, FL 34237"
    )
)

