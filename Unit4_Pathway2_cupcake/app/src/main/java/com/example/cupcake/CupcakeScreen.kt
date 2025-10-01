package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

enum class CupcakeScreen(@StringRes val title: Int) {
    Home(R.string.app_name),
    Selection(R.string.choose_flavor),
    Selectable(R.string.choose_pickup_date),
    PurchaseSummary(R.string.order_summary)
}

@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(), navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        CupcakeScreen.valueOf(backStackEntry?.destination?.route ?: CupcakeScreen.Home.name)

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Home.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(CupcakeScreen.Home.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantities,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Selection.name) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(CupcakeScreen.Selection.name) {
                val context= LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.cost,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Selectable.name)},
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel,navController)},
                    options = DataSource.cupcakeFlavors.map { id -> context.resources.getString(id)},
                    onSelectionChanged = {viewModel.setFlavor(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(CupcakeScreen.Selectable.name) {
                SelectOptionScreen(
                    subtotal = uiState.cost, onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.PurchaseSummary.name)},
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel,navController)},
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(CupcakeScreen.PurchaseSummary.name) {
                val context= LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onSendButtonClicked = { subject:String, summary:String ->
                        shareOrder(context, subject, summary)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Home.name, false)
}

private fun shareOrder(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type="text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(intent, context.getString(R.string.new_cupcake_order))
    )
}
