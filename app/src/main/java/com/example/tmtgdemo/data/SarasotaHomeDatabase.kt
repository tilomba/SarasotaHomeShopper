package com.example.tmtgdemo.data

import com.example.tmtgdemo.R

val homesDB = listOf(
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
    ),
    HomeModel(
        3,
        mutableListOf(
            R.drawable.house3_tn,
            R.drawable.house3a,
            R.drawable.house3b,
            R.drawable.house3c
        ),
        "Mid-range",
        address = "331 Amherst Ave #56, SARASOTA, FL 34232"
    )
)