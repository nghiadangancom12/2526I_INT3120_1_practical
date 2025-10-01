package org.example

fun main() {
    val brunoSong = Song("We Don't Talk About Bruno", "Encanto Cast", 2022, 1_000_000)
    brunoSong.printDescription()
    println("Popular: ${brunoSong.isPopular}")
}

class Song(
    val title: String,
    val artist: String,
    val year: Int,
    val plays: Int
){
    val isPopular: Boolean
        get() = plays >= 1000

    fun printDescription() {
        println("$title, performed by $artist, was released in $year.")
    }
}
