package com.example.cryptoapp.di

import com.example.cryptoapp.data.CryptoApi
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.CoinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.coincap.io/v2/"

class AppModule {

    val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    val coinRepository: CoinRepository by lazy {
        CoinRepositoryImpl(api)
    }
}