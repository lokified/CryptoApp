package com.example.cryptoapp.presentation

import com.example.cryptoapp.domain.models.Data

data class UiState(
    val isLoading: Boolean = false,
    var coins: List<Data> = emptyList(),
    val errorMessage: String = ""
)
