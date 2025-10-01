package com.example.superheroes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @StringRes val nameStringRes: Int,
    @StringRes val descriptionStringRes: Int,
    @DrawableRes val imageResource: Int
)
