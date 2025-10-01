package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.*

enum class LunchScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    Entree(R.string.choose_entree),
    Side(R.string.choose_side_dish),
    Accomp(R.string.choose_accompaniment),
    Checkout(R.string.order_checkout)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes title: Int,
    canGoBack: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(title)) },
        modifier = modifier,
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun LunchApp() {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val screen = backStack?.destination?.route
        ?.let { LunchScreen.valueOf(it) } ?: LunchScreen.Start
    val vm: OrderViewModel = viewModel()
    val state by vm.uiState.collectAsState()

    val resetAndGoHome: () -> Unit = {
        vm.resetOrder()
        nav.popBackStack(LunchScreen.Start.name, false)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = screen.title,
                canGoBack = nav.previousBackStackEntry != null,
                onBack = { nav.navigateUp() }
            )
        }
    ) { pad ->
        NavHost(
            navController = nav,
            startDestination = LunchScreen.Start.name,
            modifier = Modifier.padding(pad)
        ) {
            composable(LunchScreen.Start.name) {
                StartOrderScreen(
                    onStartOrderButtonClicked = {
                        nav.navigate(LunchScreen.Entree.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(LunchScreen.Entree.name) {
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = resetAndGoHome,
                    onNextButtonClicked = {
                        nav.navigate(LunchScreen.Side.name)
                    },
                    onSelectionChanged = { vm.updateEntree(it) },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }

            composable(LunchScreen.Side.name) {
                SideDishMenuScreen(
                    options = DataSource.sideDishMenuItems,
                    onCancelButtonClicked = resetAndGoHome,
                    onNextButtonClicked = {
                        nav.navigate(LunchScreen.Accomp.name)
                    },
                    onSelectionChanged = { vm.updateSideDish(it) },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }

            composable(LunchScreen.Accomp.name) {
                AccompanimentMenuScreen(
                    options = DataSource.accompanimentMenuItems,
                    onCancelButtonClicked = resetAndGoHome,
                    onNextButtonClicked = {
                        nav.navigate(LunchScreen.Checkout.name)
                    },
                    onSelectionChanged = { vm.updateAccompaniment(it) },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }

            composable(LunchScreen.Checkout.name) {
                CheckoutScreen(
                    orderUiState = state,
                    onCancelButtonClicked = resetAndGoHome,
                    onNextButtonClicked = resetAndGoHome,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium),
                        )
                )
            }
        }
    }
}
