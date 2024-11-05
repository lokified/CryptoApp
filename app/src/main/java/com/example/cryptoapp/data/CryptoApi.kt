package com.example.cryptoapp.data

import com.example.cryptoapp.domain.models.CoinResponse
import retrofit2.http.GET

interface CryptoApi {

    @GET("assets?limit=20&offset=0")
    suspend fun getCoins(): CoinResponse
}