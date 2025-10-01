package com.example.cupcake.data

data class OrderUiState(
    val count:Int=0,
    val cupcakeFlavor: String="",
    val pickupDate:String="",
    val cost:String="",
    val pickupOptions: List<String> = listOf( )
)
