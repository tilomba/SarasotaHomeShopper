package com.example.tmtgdemo.di

import com.example.tmtgdemo.repository.HouseDetailRepository
import com.example.tmtgdemo.repository.HouseDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: HouseDetailRepositoryImpl): HouseDetailRepository
}