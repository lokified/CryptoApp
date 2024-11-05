package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.CryptoApi
import com.example.cryptoapp.domain.CoinRepository
import com.example.cryptoapp.domain.models.Data
import com.example.cryptoapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CoinRepositoryImpl(
    private val api: CryptoApi
): CoinRepository {

    override fun getCoinList(): Flow<Resource<List<Data>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = api.getCoins().data
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.Error("NO network connection"))
        }
    }.flowOn(Dispatchers.IO)
}