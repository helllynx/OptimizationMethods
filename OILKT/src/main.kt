fun main(args: Array<String>) {
//    testIntersectionAreaCalculation(0.0f, 0.0f, 10.0f, 10.0f, 5f, 5f, 7.0711f)
//    testIntersectionAreaCalculation(0.0f, 1.0f, 1.0f, 1.0f, 1f, 1f, 0.5f)
    val start: Long = System.nanoTime()
    val map: Map = parse("../OIL/generated_data.txt")
    val r = 10f

    println("Area: " + map.getIntersectRectanglesArea(20f, 10f, r, map))
    println("Circle Area: ${Math.pow(r.toDouble(), 2.0) * Math.PI}")

    val end: Long = System.nanoTime()
    println((end - start)/Math.pow(10.0, -9.0))
}