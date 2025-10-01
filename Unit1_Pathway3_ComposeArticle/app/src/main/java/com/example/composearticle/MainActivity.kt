package com.example.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeArticleTheme { Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) { ComposeArticleApp() } } }
    }
}

@Composable
fun ComposeArticleApp() { ArticleCard(title = stringResource(R.string.title_jetpack_compose_tutorial),
    shortDescription = stringResource(R.string.compose_short_desc), longDescription = stringResource(R.string.compose_long_desc),
    imagePainter = painterResource(R.drawable.bg_compose_background)) }

@Composable
private fun ArticleCard(title: String, shortDescription: String, longDescription: String, imagePainter: Painter, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Image(painter = imagePainter, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(180.dp).padding(bottom = 16.dp))
        Text(text = title, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = shortDescription, fontSize = 16.sp, textAlign = TextAlign.Justify, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = longDescription, fontSize = 16.sp, textAlign = TextAlign.Justify)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ComposeArticleAppPreview() { ComposeArticleTheme { ComposeArticleApp() } }
