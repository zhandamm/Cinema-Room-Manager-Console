package cinema

class Cinema(private val rows: Int, private val cols: Int) {

    private val seats: MutableList<MutableList<Char>> = MutableList(rows) { MutableList(cols) { 'S' } }
    private val totalSeats = rows * cols
    private var currentIncome = 0


    fun buySeat(row: Int, col: Int) {

        var allSeatsCost = 0
        val frontHalfOfSeats = (row / 2) * col
        val backHalfOfSeats = totalSeats - frontHalfOfSeats
        val ticketPrice: Int


        if (seats[row - 1][col - 1] != 'B') {

            allSeatsCost += if (totalSeats <= 60 || row <= rows / 2) {
                ticketPrice = 10
                totalSeats * 10
            } else {
                ticketPrice = 8
                frontHalfOfSeats * 10 + backHalfOfSeats * 8
            }
            seats[row - 1][col - 1] = 'B'
            this.currentIncome += ticketPrice
            println("Ticket price: $${ticketPrice}")
        }

    }

    fun isCorrectInput(row: Int, col: Int): Boolean {
        if (row in 1..rows && col in 1..cols) {
            return true
        }
        return false
    }

    fun isPurchasedSeat(row: Int, col: Int): Boolean {
        return this.seats[row - 1][col - 1] == 'B'
    }

    fun printSeats() {
        val header = (1..cols).joinToString(" ")
        val seatsString = seats.mapIndexed { index, row -> "${index + 1} ${row.joinToString(" ")}\n" }.joinToString("")
        println("Cinema:")
        println("  $header\n$seatsString")
    }

    fun getPurchasedTickets(): Int {
        return seats.sumOf { it.count { c: Char -> c == 'B' } }
    }

    fun getPercentageOfPurchasedTickets(): String {
        val occupiedSeats = getPurchasedTickets()
        val percentage = occupiedSeats.toDouble() / totalSeats * 100
        return "%.2f".format(percentage)
    }

    fun getCurrentIncome(): Int {
        return currentIncome
    }

    fun getTotalIncome(): Int {
        return if (totalSeats <= 60) {
            rows * cols * 10
        } else {
            (rows / 2) * cols * 10 + (rows - rows / 2) * cols * 8
        }
    }
}

//TODO ПЕРЕНЕСТИ ТО ЧТО НИЖЕ В ДРУГОЙ КЛАСС

fun printMenu() {
    println(
        """1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit"""
    )
}

fun chooseMenuOption(cinema: Cinema) {
    while (true) {
        when (readln().toInt()) {
            1 -> cinema.printSeats()
            2 -> {
                while (true) {
                    println("Enter a row number:")
                    val selectRow = readln().toInt()
                    println("Enter a seat number in that row:")
                    val selectCol = readln().toInt()
                    if (!cinema.isCorrectInput(selectRow, selectCol)) {
                        println("Wrong input!")
                        continue
                    }
                    if (cinema.isPurchasedSeat(selectRow, selectCol)) {
                        println("That ticket has already been purchased!")
                        continue
                    }

                    cinema.buySeat(selectRow, selectCol)
                    break
                }

            }

            3 -> {
                println(
                    """Number of purchased tickets: ${cinema.getPurchasedTickets()}
Percentage: ${cinema.getPercentageOfPurchasedTickets()}%
Current income: ${'$'}${cinema.getCurrentIncome()}
Total income: ${'$'}${cinema.getTotalIncome()}"""
                )
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