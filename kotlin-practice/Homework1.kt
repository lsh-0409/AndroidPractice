fun main() {
    var count : Int = 85
    when (count) {
        in 90 .. 100 -> println("A학점")
        in 80 .. 89 -> println("B학점")
        in 70 .. 79 -> println("C학점")
        in 60 .. 69 -> println("D학점")
        else -> println("F학점")
    }
}