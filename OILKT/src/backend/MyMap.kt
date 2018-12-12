package backend

import java.lang.Float.max
import kotlin.math.min

class Data {
    companion object {
        var importMap: MyMap = MyMap()
        var inputData: ArrayList<MyCircleData> = ArrayList()
    }
}

class MyCircleData(var x: Float, var y: Float, var r: Float, var growRate: Float) {
    var calculatedArea: Double = 0.0
    var theoreticallyArea: Float = 0f
}

class IndexFloat(var value: Double) {
    var used: Boolean = false
    var subMap: MutableList<MutableList<IndexFloat>> = mutableListOf()
}

class MyMap(
    var map: ArrayList<List<IndexFloat>> = arrayListOf(),
    var mapType: MapType = MapType.OIL,
    var height: Int = 0,
    var width: Int = 0,
    var size: Int = 0
) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(circleIndex: Int) {
        val startX =
            (max(Data.inputData[circleIndex].x - Data.inputData[circleIndex].r, 0f) / Data.importMap.width).toInt()
        val startY =
            (max(Data.inputData[circleIndex].y - Data.inputData[circleIndex].r, 0f) / Data.importMap.height).toInt()
        val endX = min(
            Data.importMap.size,
            ((max(
                Data.inputData[circleIndex].x + Data.inputData[circleIndex].r,
                0f
            ) / Data.importMap.width).toInt() + 1)
        )
        //TODO add map dimension sizes
        val endY =
            min(
                Data.importMap.size,
                ((max(
                    Data.inputData[circleIndex].y + Data.inputData[circleIndex].r,
                    0f
                ) / Data.importMap.height).toInt() + 1)
            )

        try {
            for (i in startY until endY) {
                for (j in startX until endX) {
                    if (Data.importMap.map[i][j].used)
                        continue
                    intersectionArea(
                        Rectangle(
                            (j * Data.importMap.width).toFloat(),
                            (i * Data.importMap.height).toFloat(),
                            Data.importMap.width.toFloat(),
                            Data.importMap.height.toFloat()
                        ), circleIndex,
                        i,
                        j
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun newCalculation(periodCount: Int) {
//    fullCleanCircles()
    repeat(periodCount) {
        fullCleanMap()

        for (c in 0 until Data.inputData.size) {
            Data.importMap.getIntersectRectanglesArea(c)
        }
        riseCircleRadius()
    }
//    fullCleanCircles()
}

fun aggregateSpace(): Double {
    var space = 0.0
    for (c in Data.inputData) {
        space += c.calculatedArea
    }

    return space
}

fun riseCircleRadius() {
    for (i in 0 until Data.inputData.size) {
        Data.inputData[i].r += Data.inputData[i].growRate
    }
}

//TODO need something more effective
fun fullCleanMap() {
    for (row in 0 until Data.importMap.map.size) {
        for (column in 0 until Data.importMap.map.size) {
            Data.importMap.map[row][column].subMap = mutableListOf()
            Data.importMap.map[row][column].used = false
        }
    }
}

//fun fullCleanCircles() {
//    for (i in 0 until Data.inputData.size) {
//        Data.inputData[i].calculatedArea = 0.0
//    }
//}
