package backend

import java.lang.Float.max
import kotlin.math.min

class Data {
    companion object {
        var importMap: MyMap = MyMap()
        var inputData: ArrayList<Circle> = ArrayList()
        var outputData: ArrayList<AreaOutType> = ArrayList()
    }
}

class IndexFloat {
    var value: Float
    var used: Boolean
    var subList: List<IndexFloat> = listOf()

    constructor(value: Float, used: Boolean) {
        this.value = value
        this.used = used
    }

    constructor(value: Float, used: Boolean, subList: List<IndexFloat>) {
        this.value = value
        this.used = used
        this.subList = subList
    }
}

class MyMap(
    var map: List<List<IndexFloat>> = listOf(),
    var mapType: MapType = MapType.OIL,
    var height: Int = 0,
    var width: Int = 0,
    var size: Int = 0
) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(circle: Circle, oilMap: MyMap): Float {
        val startX = (max(circle.x - circle.r, 0f) / oilMap.width).toInt()
        val startY = (max(circle.y - circle.r, 0f) / oilMap.height).toInt()
        val endX = min(Data.importMap.size, ((max(circle.x + circle.r, 0f) / oilMap.width).toInt() + 1))
        //TODO add map dimention sizes
        val endY = min(Data.importMap.size, ((max(circle.y + circle.r, 0f) / oilMap.height).toInt() + 1))

        var area = 0f
        var count = 0

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
                    ) * oilMap.map[i][j].value
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
                Data.importMap.getIntersectRectanglesArea(c, Data.importMap),
                (Math.pow(c.r.toDouble(), 2.0) * Math.PI).toFloat()
            )
        )
    }
}


