package backend

import java.lang.Float.max
import kotlin.math.abs

class OilMap {
    var array: ArrayList<FloatArray>
    var mapType: MapType
    var height: Int
    var width: Int

    constructor(array: ArrayList<FloatArray>, mapType: MapType, height: Int, width: Int) {
        this.array = array
        this.mapType = mapType
        this.height = height
        this.width = width
    }

    constructor() {
        this.array = ArrayList()
        this.mapType = MapType.OIL
        this.height = 0
        this.width = 0
    }

    enum class MapType { OIL, PORO }

    companion object {
        lateinit var importOilMap: OilMap
        var inputData: ArrayList<Circle> = ArrayList()
        var outputData: ArrayList<AreaOutType> = ArrayList()
    }

    fun getIntersectRectanglesArea(circle: Circle, oilMap: OilMap): Float {
        val startX = (max(circle.x - circle.r, 0f) / oilMap.width).toInt()
        val startY = (max(circle.y - circle.r, 0f) / oilMap.height).toInt()
        val endX = ((max(circle.x + circle.r, 0f) / oilMap.width).toInt() + 1)
        val endY = ((max(circle.y + circle.r, 0f) / oilMap.height).toInt() + 1)

        var area = 0f

//        println("getIntersectRectangles")
//        println("startX: $startX | " + startX*oilMap.width)
//        println("endX: $endX | " + endX * oilMap.width)
//        println("startY: $startY | " + startY * oilMap.height)
//        println("endY: $endY | " + endY * oilMap.height)

        try {
            for (i in startY until endY) {
                for (j in startX until endX) {
                    area += intersectionArea(
                        Rectangle(
                            (j * oilMap.width).toFloat(),
                            (i * oilMap.height).toFloat(),
                            oilMap.width.toFloat(),
                            oilMap.height.toFloat()
                        ), circle
                    )
//                    * oilMap.array[i][j]
//                    println("$i $j")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return area
    }
}


fun massiveTest(n: Int) {
    var count = 0
    val e = 10000
    for (i in 1..n step 1) {
        val r = RoundToDecimal((Math.random() * i).toFloat())
        val x = r + (Math.random() * i).toFloat()
        val y = r + (Math.random() * i).toFloat()
        val temp1 = (OilMap.importOilMap.getIntersectRectanglesArea(Circle(x, y, r), OilMap.importOilMap))
        val temp2 = (Math.pow(r.toDouble(), 2.0) * Math.PI).toFloat()

        if (abs(temp1 - temp2) > e) {
            count += 1
            println("=========================")
            println("i: $i")
            println("r: $r")
            println("x: $x")
            println("y: $y")
            println(temp1)
            println(temp2)
        }

    }

    println(count)
}

fun calculate(circles: ArrayList<Circle>) {
    for (c in circles) {
        OilMap.outputData.add(AreaOutType(c, OilMap.importOilMap.getIntersectRectanglesArea(c, OilMap.importOilMap)))
    }
}