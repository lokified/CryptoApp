package com.example.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.CoinRepository
import com.example.cryptoapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val coinRepository: CoinRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val state = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                getCoins()
                delay(1000L * 10)
            }
        }
    }

    fun onSearchCoin(searchTerm: String) {
        _uiState.value.coins = _uiState.value.coins.filter {
            it.name.lowercase().contains(searchTerm.lowercase())
        }
    }


    private fun getCoins() {
        viewModelScope.launch {
            coinRepository.getCoinList().collect { result ->
                when(result) {
                    is Resource.Error -> _uiState.value =  UiState(errorMessage = result.message)
                    is Resource.Loading -> _uiState.value =  UiState(isLoading = true)
                    is Resource.Success -> {
                        _uiState.value = UiState(coins = result.data)
                    }
                }
            }
        }
    }
}

class MainViewModelProvider(
    val coinRepository: CoinRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(coinRepository) as T
    }
}