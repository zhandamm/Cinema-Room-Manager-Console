package cinema

class Cinema(private val rows: Int, private val cols: Int) {

    private val seats: MutableList<MutableList<Char>> = MutableList(rows) { MutableList(cols) { 'S' } }
    private val seatsAmount = rows * cols


    fun buySeat(row: Int, col: Int) {

        var allSeatsCost = 0
        val frontHalfOfSeats = (row / 2) * col
        val backHalfOfSeats = seatsAmount - frontHalfOfSeats
        val ticketPrice: Int


        if (seats[row - 1][col - 1] != 'B') {

            allSeatsCost += if (seatsAmount <= 60 || row <= rows / 2) {
                ticketPrice = 10
                seatsAmount * 10
            } else {
                ticketPrice = 8
                frontHalfOfSeats * 10 + backHalfOfSeats * 8
            }
            seats[row - 1][col - 1] = 'B'
            println("Ticket price: $${ticketPrice}")
        }

    }


    fun printSeats() {
        val header = (1..cols).joinToString(" ")
        val seatsString = seats.mapIndexed { index, row -> "${index + 1} ${row.joinToString(" ")}\n" }.joinToString("")
        println("Cinema:")
        println("  $header\n$seatsString")
    }
}

//TODO ПЕРЕНЕСТИ ТО ЧТО НИЖЕ В ДРУГОЙ КЛАСС

fun printMenu() {
    println(
        """1. Show the seats
2. Buy a ticket
0. Exit"""
    )
}

fun chooseMenuOption(cinema: Cinema) {
    while (true) {
        when (readln().toInt()) {
            1 -> cinema.printSeats()
            2 -> {
                println("Enter a row number:")
                val selectRow = readln().toInt()
                println("Enter a seat number in that row:")
                val selectCol = readln().toInt()
                cinema.buySeat(selectRow, selectCol)
            }

            0 -> break
        }
        printMenu()
    }

}

fun displayMenuAndChooseOption(cinema: Cinema) {
    printMenu()
    chooseMenuOption(cinema)
}


fun main() {
    val cinema = createCinema()
    displayMenuAndChooseOption(cinema)
}


fun createCinema(): Cinema {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val cols = readln().toInt()
    return Cinema(rows, cols)
}