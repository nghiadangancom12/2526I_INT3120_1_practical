package com.example.cupcake.test

import android.icu.util.Calendar
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import com.example.cupcake.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class CupcakeScreenNavigationTest{
    @get:Rule val composeTestRule=createAndroidComposeRule<ComponentActivity>()
    private lateinit var testNavController:TestNavHostController

    @Before fun setupCupcakeNavHost(){
        composeTestRule.setContent {
            testNavController= TestNavHostController(LocalContext.current).apply{
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController=testNavController)
        }
    }

    @Test fun cupcakeNavHost_verifyStartDestination(){
        testNavController.assertCurrentRouteName(CupcakeScreen.Home.name)
    }

    @Test fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen(){
        val backText=composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .assertExists().performClick()
        testNavController.assertCurrentRouteName(CupcakeScreen.Selection.name)
    }

    @Test fun cupcakeNavHost_clickNextOnFlavorScreen_navigatesToPickupScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsDisplayed()
            .performClick( )
        testNavController.assertCurrentRouteName(CupcakeScreen.Selectable.name)
    }

    @Test fun cupcakeNavHost_clickBackOnFlavorScreen_navigatesToStartOrderScreen( ){
        navigateToFlavorScreen()
        performNavigateUp()
        testNavController.assertCurrentRouteName(CupcakeScreen.Home.name)
    }

    @Test fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartOrderScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        testNavController.assertCurrentRouteName(CupcakeScreen.Home.name)
    }

    @Test fun cupcakeNavHost_clickNextOnPickupScreen_navigatesToSummaryScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        testNavController.assertCurrentRouteName(CupcakeScreen.PurchaseSummary.name)
    }

    @Test fun cupcakeNavHost_clickBackOnPickupScreen_navigatesToFlavorScreen(){
        navigateToPickupScreen()
        performNavigateUp()
        testNavController.assertCurrentRouteName(CupcakeScreen.Selection.name)
    }

    @Test fun cupcakeNavHost_clickCancelOnPickupScreen_navigatesToStartOrderScreen(){
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel)
            .assertIsDisplayed().performClick()
        testNavController.assertCurrentRouteName(CupcakeScreen.Home.name)
    }

    @Test fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartOrderScreen(){
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        testNavController.assertCurrentRouteName(CupcakeScreen.Home.name)
    }

    private fun navigateToFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
    }

    private fun navigateToPickupScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun navigateToSummaryScreen( ){
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    private fun performNavigateUp(){
        val backText=composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun getFormattedDate():String{
        val calendar=Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE,1)
        val formatter=SimpleDateFormat("E MMM d",Locale.getDefault())
        return formatter.format(calendar.time)
    }
}
