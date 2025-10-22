package com.example.flightsearch.ui

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.FlightSearchDatabase
import com.example.flightsearch.data.Airport
import com.example.flightsearch.datastore.UserPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp() {
    val context = LocalContext.current
    // Simple manual DI
    val db = remember { FlightSearchDatabase.get(context) }
    val repo = remember { FlightRepository(db.airportDao(), db.favoriteDao()) }
    val prefs = remember { UserPreferences(context) }
    val vm: FlightSearchViewModel = viewModel(
        factory = SimpleVMFactory { FlightSearchViewModel(repo, prefs) }
    )
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Flight Search") }) }
    ) { inner ->
        Box(Modifier.padding(inner).fillMaxSize()) {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                var tf by remember { mutableStateOf(TextFieldValue(state.search)) }
                LaunchedEffect(state.search) { if (tf.text != state.search) tf = TextFieldValue(state.search) }

                OutlinedTextField(
                    value = tf,
                    onValueChange = {
                        tf = it
                        vm.setSearch(it.text)
                    },
                    label = { Text("Enter airport name or IATA") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                if (state.search.isBlank()) {
                    // Hiển thị Favorites
                    Text("Favorites", style = MaterialTheme.typography.titleMedium)
                    LazyColumn {
                        items(state.favorites) { fav ->
                            ListItem(
                                headlineContent = { Text("${fav.departure_code} → ${fav.destination_code}") }
                            )
                            Divider()
                        }
                    }
                } else {
                    // Gợi ý (overlay đơn giản — có thể nâng cấp Box/animated)
                    if (state.suggestions.isNotEmpty() && state.selectedDeparture == null) {
                        Text("Suggestions", style = MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(state.suggestions) { a ->
                                ListItem(
                                    headlineContent = { Text("${a.iata_code} — ${a.name}") },
                                    modifier = Modifier.clickable { vm.selectDeparture(a) }
                                )
                                Divider()
                            }
                        }
                    }

                    state.selectedDeparture?.let { dep ->
                        Spacer(Modifier.height(12.dp))
                        Text("Flights from ${dep.iata_code} — ${dep.name}", style = MaterialTheme.typography.titleMedium)
                        LazyColumn {
                            items(state.destinations) { dst ->
                                RouteRow(
                                    departure = dep,
                                    destination = dst,
                                    onToggle = { vm.toggleFavorite(dst) }
                                )
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RouteRow(
    departure: Airport,
    destination: Airport,
    onToggle: () -> Unit
) {
    ListItem(
        headlineContent = { Text("${departure.iata_code} → ${destination.iata_code}") },
        supportingContent = { Text("${departure.name} → ${destination.name}") },
        trailingContent = {
            // Nút “star” minh hoạ (bạn có thể đổi trạng thái theo flow favoriteOf)
            IconButton(onClick = onToggle) {
                Icon(Icons.Outlined.StarBorder, contentDescription = "Toggle favorite")
            }
        }
    )
}

class SimpleVMFactory<T>(val create: () -> T) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <U : androidx.lifecycle.ViewModel> create(modelClass: Class<U>): U = create() as U
}
