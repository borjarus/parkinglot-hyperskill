package parking

import java.awt.Color

class Parking {
    val spots: MutableList<Spot?> = MutableList(2) { null }

    data class Spot(val plateNo: String, val color: String)

    init {
        spots[0] = Spot("KA-01-HH-1234", "Blue")
    }

    fun park(plateNo: String, color: String): String {
        val index = spots.indexOfFirst { it == null }
        spots[index] = Spot(plateNo, color)
        return "$color car parked in spot ${index + 1}."

    }

    fun leave(spot: Int): String = if (isCarInSpot(spot))
         "Spot $spot is free."
    else
         "There is no car in spot $spot."

    private fun isCarInSpot(spot: Int) = spots[spot - 1] != null

}

fun main() {
    val input = readln().substring(2).split(' ')
    val parking = Parking()
    val result = when (input.first()) {
        "park" -> parking.park(input[1], input[2])
        "leave" -> parking.leave(input[1].toInt())
        else -> ""
    }
    println(result)
}


