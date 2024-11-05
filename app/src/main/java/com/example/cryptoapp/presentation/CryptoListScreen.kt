package com.example.cryptoapp.presentation

import android.icu.text.DecimalFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.domain.models.Data

@Composable
fun CryptoListScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    val state by mainViewModel.state.collectAsState()

    val (value, setValue) = remember {
        mutableStateOf("")
    }

    LaunchedEffect(state) {
        if (state.errorMessage.isNotEmpty()) {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    Column (
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ){
        SearchInput(
            value = value,
            onValueChange = {
                setValue(it)
                mainViewModel.onSearchCoin(it)
            }
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        CryptoListContent(cryptoList = state.coins)
    }
}

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Search Crypto")
        }
    )
}

@Composable
fun CryptoListContent(
    modifier: Modifier = Modifier,
    cryptoList: List<Data>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "# Name")
                Text(text = "24h")
                Text(text = "Price")
            }
        }

        itemsIndexed(cryptoList) { index, crypto ->
            CryptoItem(position = index + 1, crypto = crypto)
        }
    }
}

@Composable
fun CryptoItem(
    modifier: Modifier = Modifier,
    crypto: Data,
    position: Int
) {

    val textColor = if (crypto.changePercent24Hr.startsWith("-")) Color.Red else Color.Green

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$position. "+ crypto.name)
        Text(text = String.format("%.2f", crypto.changePercent24Hr.toFloat()), color = textColor)
        Text(text = "$ ${String.format("%.2f", crypto.priceUsd.toFloat())}", color = textColor)
    }
}