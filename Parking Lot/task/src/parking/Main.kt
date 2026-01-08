package parking

data class Car(val regNumber: String, val color: String)

var spots: Array<Car?>? = null

fun main() {
    generateSequence { readln().trim().split(" ") }
        .takeWhile { it[0] != "exit" }
        .forEach { processCommand(it) }
}

fun checkIfParkingExists(action: (Array<Car?>) -> Unit) {
    if (spots == null) {
        println("Sorry, a parking lot has not been created.")
    } else {
        action(spots!!)
    }
}

fun handlePark(args: List<String>): Unit = checkIfParkingExists { parkingSpots ->
    val (regNumber, color) = args
    val emptySpot = parkingSpots.indexOfFirst { it == null }
    when {
        emptySpot != -1 -> {
            parkingSpots[emptySpot] = Car(regNumber, color)
            println("$color car parked in spot ${emptySpot + 1}.")
        }
        else -> println("Sorry, the parking lot is full.")
    }
}

fun handleLeave(spotNumber: Int) = checkIfParkingExists { parkingSpots ->
    val index = spotNumber - 1
    parkingSpots[index]?.let {
        parkingSpots[index] = null
        println("Spot $spotNumber is free.")
    } ?: println("There is no car in spot $spotNumber.")
}

fun handleStatus() = checkIfParkingExists { parkingSpots ->
    val occupiedSpots = parkingSpots
        .mapIndexedNotNull { index, car ->
            car?.let { "${index + 1} ${it.regNumber} ${it.color}" }
        }

    occupiedSpots.takeIf { it.isNotEmpty() }
        ?.forEach { println(it) }
        ?: println("Parking lot is empty.")
}

fun handleRegNumberByColor(colorQuery: String) = checkIfParkingExists { parkingSpots ->
    val result = parkingSpots
        .mapNotNull { car -> car?.takeIf { it.color.equals(colorQuery, true) }?.regNumber }

    if (result.isEmpty()) {
        println("No cars with color ${colorQuery.uppercase()} were found.")
    } else  {
        println(result.joinToString(", "))
    }
}

fun handleSpotByColor(colorQuery: String) = checkIfParkingExists { parkingSpots ->
    val result = parkingSpots
        .mapIndexedNotNull { index, car ->
            car?.takeIf { it.color.equals(colorQuery, true) }?.let { index + 1}
        }

    if (result.isEmpty()) {
        println("No cars with color ${colorQuery.uppercase()} were found.")
    } else  {
        println(result.joinToString(", "))
    }
}

fun handleSpotByRegNumber(regNumberQuery: String) = checkIfParkingExists { parkingSpots ->
    val index = parkingSpots.indexOfFirst { it?.regNumber == regNumberQuery }
    if (index == -1) {
        println("No cars with registration number $regNumberQuery were found.")
    } else  {
        println(index + 1)
    }
}

fun processCommand(input: List<String>): Unit {
    when (input[0]) {
        "create" -> {
            val spotsCount = input[1].toInt()
            spots = arrayOfNulls(spotsCount)
            println("Created a parking lot with $spotsCount spots.")
        }
        "park" -> handlePark(input.drop(1))
        "leave" -> handleLeave(input[1].toInt())
        "status" -> handleStatus()
        "reg_by_color" -> handleRegNumberByColor(input[1])
        "spot_by_color" -> handleSpotByColor(input[1])
        "spot_by_reg" -> handleSpotByRegNumber(input[1])
    }
}

