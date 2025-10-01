package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Artwork(val title: String, val artist: String, val year: Int, val imageRes: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) { ArtSpaceApp() }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val artworks = listOf(
        Artwork("Mona Lisa", "Leonardo da Vinci", 1503, R.drawable.mona_lisa),
        Artwork("Starry Night", "Vincent van Gogh", 1889, R.drawable.starry_night),
        Artwork("The Scream", "Edvard Munch", 1893, R.drawable.the_scream)
    )
    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    ArtSpaceColumnLayout(
        artwork = currentArtwork,
        onNext = { currentIndex = (currentIndex + 1) % artworks.size },
        onPrevious = { currentIndex = if (currentIndex - 1 < 0) artworks.lastIndex else currentIndex - 1 }
    )
}

@Composable
fun ArtSpaceColumnLayout(artwork: Artwork, onNext: () -> Unit, onPrevious: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = artwork.imageRes),
            contentDescription = artwork.title,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(artwork.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("${artwork.artist}, ${artwork.year}", fontSize = 17.sp)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onPrevious) { Text("Previous") }
            Button(onClick = onNext) { Text("Next") }
        }
    }
}

@Composable
fun ArtSpaceRowLayout(artwork: Artwork, onNext: () -> Unit, onPrevious: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = artwork.imageRes),
            contentDescription = artwork.title,
            modifier = Modifier.weight(1f).fillMaxHeight()
        )
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(artwork.title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("${artwork.artist}, ${artwork.year}", fontSize = 22.sp)
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = onPrevious) { Text("Previous") }
                Button(onClick = onNext) { Text("Next") }
            }
        }
    }
}
