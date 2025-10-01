package org.example

fun main(){val groupedEvents = events.groupBy { it.daypart }
    groupedEvents.forEach { (daypart, events) ->
        println("$daypart: ${org.example.events.size} events")
    }
}
