package com.example.cryptoapp.domain

import com.example.cryptoapp.domain.models.Data
import com.example.cryptoapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinList(): Flow<Resource<List<Data>>>
}