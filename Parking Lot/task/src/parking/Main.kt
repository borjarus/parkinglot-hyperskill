package parking

data class Car(val regNumber: String, val color: String)

fun main() {
    val spots = arrayOfNulls<Car>(2)
    spots[0] = Car("IT-DOESNT_MATTER", "Blue") // initial car

    val input  = readln().trim().split(" ")

    when (input[0]) {
        "park" -> {
            val regNumber = input[1]
            val color = input[2]

            if (spots[1] == null){
                spots[1] = Car(regNumber, color)
                println("$color car parked in spot 2.")
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

