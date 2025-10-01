package com.example.thirtydaysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.thirtydaysapp.ui.theme.ThirtyDaysAppTheme
import com.example.thirtydaysapp.ui.theme.TipCard
import com.example.thirtydaysapp.ui.theme.TipData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirtyDaysAppTheme {
                TipList()
            }
        }
    }
}

@Composable
fun TipList() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(TipData.tips.size) { index ->
            TipCard(tip = TipData.tips[index])
        }
    }
}
