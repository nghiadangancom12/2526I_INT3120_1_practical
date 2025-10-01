package com.example.superheroes

object HeroesRepository{
    val heroes=listOf(
        Hero(
            nameStringRes=R.string.hero1,
            descriptionStringRes=R.string.description1,
            imageResource=R.drawable.android_superhero1
        ),
        Hero(
            nameStringRes= R.string.hero2,
            descriptionStringRes=R.string.description2,
            imageResource= R.drawable.android_superhero2
        ),
        Hero(
            nameStringRes=R.string.hero3,
            descriptionStringRes= R.string.description3,
            imageResource=R.drawable.android_superhero3
        ),
        Hero(
            nameStringRes=R.string.hero4,
            descriptionStringRes=R.string.description4,
            imageResource= R.drawable.android_superhero4
        ),
        Hero(
            nameStringRes =R.string.hero5,
            descriptionStringRes =R.string.description5,
            imageResource= R.drawable.android_superhero5
        ),
        Hero(
            nameStringRes=R.string.hero6,
            descriptionStringRes= R.string.description6,
            imageResource=R.drawable.android_superhero6
        )
    )
}
