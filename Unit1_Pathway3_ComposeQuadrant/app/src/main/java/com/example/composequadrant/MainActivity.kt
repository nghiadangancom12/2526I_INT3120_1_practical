package com.example.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeQuadrantTheme { Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) { ComposeQuadrantApp() } } }
    }
}

@Composable
fun ComposeQuadrantApp() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            ComposableInfoCard(stringResource(R.string.first_title), stringResource(R.string.first_description), Color(0xFFEADDFF), Modifier.weight(1f))
            ComposableInfoCard(stringResource(R.string.second_title), stringResource(R.string.second_description), Color(0xFFD0BCFF), Modifier.weight(1f))
        }
        Row(Modifier.weight(1f)) {
            ComposableInfoCard(stringResource(R.string.third_title), stringResource(R.string.third_description), Color(0xFFB69DF8), Modifier.weight(1f))
            ComposableInfoCard(stringResource(R.string.fourth_title), stringResource(R.string.fourth_description), Color(0xFFF6EDFF), Modifier.weight(1f))
        }
    }
}

@Composable
private fun ComposableInfoCard(title: String, description: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxSize().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp), color = MaterialTheme.colorScheme.onSurface)
            Text(text = description, textAlign = TextAlign.Justify, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ComposeQuadrantAppPreview() {
    ComposeQuadrantTheme { ComposeQuadrantApp() }
}
