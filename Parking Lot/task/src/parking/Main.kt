package parking

data class Car(val regNumber: String, val color: String)

fun main() {
    val spots = arrayOfNulls<Car>(20)

    while (true) {
        val input  = readln().trim().split(" ")

        when (input[0]) {
            "exit" -> break
            "park" -> {
                val regNumber = input[1]
                val color = input[2]

                val emptySpot = spots.indexOfFirst { it == null }
                if (emptySpot != -1) {
                    spots[emptySpot] = Car(regNumber, color)
                    println("$color car parked in spot ${emptySpot + 1}.")
                } else {
                    println("Sorry, the parking lot is full.")
                }
            }
            "leave" -> {
                val spotNumber = input[1].toInt()

                try {
                    val index = spotNumber - 1
                    if (spots[index] == null){
                        println("There is no car in spot $spotNumber.")
                    } else {
                        spots[index] = null
                        println("Spot $spotNumber is free.")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    println("There is no car in spot $spotNumber.")
                }
            }
        }
    }
}