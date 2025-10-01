package org.example

fun main() {
    var favoriteActor: String? = "Sandra Oh"
    var secondActor: String? = null

    println(favoriteActor?.length)
    println(secondActor?.length)
    println(favoriteActor!!.length)
    val lengthOfFirst = if(favoriteActor != null) favoriteActor.length else 0
    val lengthOfSecond = if(secondActor != null) secondActor.length else 0
    println("$lengthOfFirst, $lengthOfSecond")
    val lengthUsingElvis = secondActor?.length ?: 0
    println(lengthUsingElvis)
    println(favoriteActor?.uppercase() ?: "Unknown")
    println(secondActor?.uppercase() ?: "Unknown")
}
