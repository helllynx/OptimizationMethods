package backend

import java.lang.Float.max

class Data {
    companion object {
        lateinit var importOilMap: OilMap
        var inputData: ArrayList<Circle> = ArrayList()
        var outputData: ArrayList<AreaOutType> = ArrayList()
    }
}

class OilMap {
    var array: ArrayList<FloatArray>
    var mapType: MapType
    var height: Int
    var width: Int
    var size: Int

    constructor(
        array: ArrayList<FloatArray>,
        mapType: MapType,
        height: Int,
        width: Int,
        size: Int
    ) {
        this.array = array
        this.mapType = mapType
        this.height = height
        this.width = width
        this.size = size
    }

    constructor() {
        this.array = ArrayList()
        this.mapType = MapType.OIL
        this.height = 0
        this.width = 0
        this.size = 0
    }

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(circle: Circle, oilMap: OilMap): Float {
        val startX = (max(circle.x - circle.r, 0f) / oilMap.width).toInt()
        val startY = (max(circle.y - circle.r, 0f) / oilMap.height).toInt()
        val endX = ((max(circle.x + circle.r, 0f) / oilMap.width).toInt() + 1)
        val endY = ((max(circle.y + circle.r, 0f) / oilMap.height).toInt() + 1)

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
                    ) * oilMap.array[i][j]
                    count += 1
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println(count)
        return area
    }
}

fun calculate(circles: ArrayList<Circle>) {
    for (c in circles) {
        Data.outputData.add(
            AreaOutType(
                c,
                Data.importOilMap.getIntersectRectanglesArea(c, Data.importOilMap),
                (Math.pow(c.r.toDouble(), 2.0) * Math.PI).toFloat()
            )
        )
    }
}


