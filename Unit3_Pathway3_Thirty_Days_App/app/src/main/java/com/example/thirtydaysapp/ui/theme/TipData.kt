package com.example.thirtydaysapp.ui.theme

import com.example.thirtydaysapp.R


object TipData {
    val tips = List(30) { day ->
        Tip(
            day = day + 1,
            title = "Tip for Day ${day + 1}",
            description = "This is a description for day ${day + 1}. Keep practicing!",
            imageResId = R.drawable.android_superhero1 // Thay bằng ảnh thật trong drawable
        )
    }
}