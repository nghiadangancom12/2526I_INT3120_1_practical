package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val initialOrderUiState = OrderUiState(
        count = 6, cupcakeFlavor = "Vanilla",
        pickupDate = "Wed Jul 21", cost = "$100",
        pickupOptions = listOf()
    )

    @Test
    fun startOrderScreen_verifyContent() {
        composeRule.setContent { StartOrderScreen(quantityOptions = DataSource.quantities, onNextButtonClicked = {}) }
        DataSource.quantities.forEach { composeRule.onNodeWithStringId(it.first).assertIsDisplayed() }
    }

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavors = listOf("Vanilla","Chocolate","Hazelnut","Cookie","Mango")
        val subtotal = "$100"
        composeRule.setContent { SelectOptionScreen(subtotal = subtotal, options = flavors) }

        flavors.forEach { flavor -> composeRule.onNodeWithText(flavor).assertIsDisplayed() }
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.subtotal_price, subtotal)
        ).assertIsDisplayed()
        composeRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun selectOptionScreen_optionSelected_NextButtonEnabled() {
        val flavours = listOf("Vanilla","Chocolate","Hazelnut","Cookie","Mango")
        val subTotal = "$100"
        composeRule.setContent { SelectOptionScreen(subtotal = subTotal, options = flavours) }
        composeRule.onNodeWithText("Vanilla").performClick()
        this@CupcakeOrderScreenTest.composeRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun summaryScreen_verifyContentDisplay() {
        composeRule.setContent {
            OrderSummaryScreen(
                orderUiState = initialOrderUiState,
                onCancelButtonClicked = {}, onSendButtonClicked = { _, _ -> },
            )
        }
        composeRule.onNodeWithText(initialOrderUiState.cupcakeFlavor).assertIsDisplayed()
        composeRule.onNodeWithText(initialOrderUiState.pickupDate).assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(R.string.subtotal_price, initialOrderUiState.cost)
        ).assertIsDisplayed()
    }
}
