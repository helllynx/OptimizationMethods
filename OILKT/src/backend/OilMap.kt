package backend

import java.lang.Float.max
import kotlin.math.min

class Data {
    companion object {
        lateinit var importOilMap: OilMap
        var inputData: ArrayList<Circle> = ArrayList()
        var outputData: ArrayList<AreaOutType> = ArrayList()
    }
}

class OilMap(
    var array: List<List<Float>> = listOf(),
    var mapType: MapType = MapType.OIL,
    var height: Int = 0,
    var width: Int = 0,
    var size: Int = 0
) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(circle: Circle, oilMap: OilMap): Float {
        val startX = (max(circle.x - circle.r, 0f) / oilMap.width).toInt()
        val startY = (max(circle.y - circle.r, 0f) / oilMap.height).toInt()
        val endX = min(Data.importOilMap.size, ((max(circle.x + circle.r, 0f) / oilMap.width).toInt() + 1))
        val endY = min(Data.importOilMap.size, ((max(circle.y + circle.r, 0f) / oilMap.height).toInt() + 1))

        var area = 0f
        var count = 0


        //TODO fix out of bounds, add mapo sizes x and y
        println("startX: $startX")
        println("endX: $endX")
        println("startY: $startY")
        println("endY: $endY")

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
                    ) * oilMap.array[i][j]
                    count += 1
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return area
    }
}

fun calculate(circles: ArrayList<Circle>) {
    circles.forEach { c ->
        Data.outputData.add(
            AreaOutType(
                c,
                Data.importOilMap.getIntersectRectanglesArea(c, Data.importOilMap),
                (Math.pow(c.r.toDouble(), 2.0) * Math.PI).toFloat()
            )
        )
    }
}


