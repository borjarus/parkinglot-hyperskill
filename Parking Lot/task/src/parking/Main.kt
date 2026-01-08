package parking

data class Car(val regNumber: String, val color: String)

var spots: Array<Car?>? = null

fun main() {
    generateSequence { readln().trim().split(" ") }
        .takeWhile { it[0] != "exit" }
        .forEach { processCommand(it) }
}

fun handlePark(args: List<String>): Unit {
    spots ?: run {
        println("Sorry, a parking lot has not been created.")
        return
    }

    val (regNumber, color) = args
    val emptySpot = spots!!.indexOfFirst { it == null }
    when {
        emptySpot != -1 -> {
            spots!![emptySpot] = Car(regNumber, color)
            println("$color car parked in spot ${emptySpot + 1}.")
        }
        else -> println("Sorry, the parking lot is full.")
    }
}

fun handleLeave(spotNumber: Int): Unit {
    spots ?: run {
        println("Sorry, a parking lot has not been created.")
        return
    }

    val index = spotNumber - 1
    spots!![index]?.let {
        spots!![index] = null
        println("Spot $spotNumber is free.")
    } ?: println("There is no car in spot $spotNumber.")
}

fun handleStatus(): Unit {
    spots ?: run {
        println("Sorry, a parking lot has not been created.")
        return
    }

    val occupiedSpots = spots!!
        .mapIndexedNotNull { index, car ->
            car?.let { "${index + 1} ${it.regNumber} ${it.color}" }
        }

    occupiedSpots.takeIf { it.isNotEmpty() }
        ?.forEach { println(it) }
        ?: println("Parking lot is empty.")
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
    }
}