fun main() {
    val fuck: BooleanArray = BooleanArray(10)

    fuck[0] = true
    fuck[2] = true
    fuck[3] = true

    fuck.forEach { println(it) }
}