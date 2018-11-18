package backend

import java.lang.Float.max
import kotlin.math.min

class Data {
    companion object {
        var importMap: MyMap = MyMap()
        var inputData: ArrayList<MyCircleData> = ArrayList()
    }
}

class MyCircleData(var x: Float, var y: Float, var r: Float) {
    var calculatedArea: Float = 0f
    var theoreticallyArea: Float = 0f
}

class IndexFloat {
    var value: Float
    var used: Boolean = false
    var subMap: MutableList<MutableList<IndexFloat>> = mutableListOf()

    constructor(value: Float) {
        this.value = value
    }
}

class MyMap(
    var map: ArrayList<List<IndexFloat>> = arrayListOf(),
    var mapType: MapType = MapType.OIL,
    var height: Int = 0,
    var width: Int = 0,
    var size: Int = 0
) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(myMyCircleData: MyCircleData, oilMap: MyMap) {
        val startX = (max(myMyCircleData.x - myMyCircleData.r, 0f) / oilMap.width).toInt()
        val startY = (max(myMyCircleData.y - myMyCircleData.r, 0f) / oilMap.height).toInt()
        val endX = min(Data.importMap.size, ((max(myMyCircleData.x + myMyCircleData.r, 0f) / oilMap.width).toInt() + 1))
        //TODO add map dimention sizes
        val endY =
            min(Data.importMap.size, ((max(myMyCircleData.y + myMyCircleData.r, 0f) / oilMap.height).toInt() + 1))

        try {
            for (i in startY until endY) {
                for (j in startX until endX) {
                    if (oilMap.map[i][j].used)
                        continue
                    intersectionArea(
                        Rectangle(
                            (j * oilMap.width).toFloat(),
                            (i * oilMap.height).toFloat(),
                            oilMap.width.toFloat(),
                            oilMap.height.toFloat()
                        ), myMyCircleData,
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

//fun calculate(circles: ArrayList<MyCircleData>) {
//    circles.forEach { c ->
//        Data.outputData.add(
//            AreaOutType(
//                c.circle,
//                Data.importMap.getIntersectRectanglesArea(c.circle, Data.importMap),
//                (Math.pow(c.myCircle.circle.r.toDouble(), 2.0) * Math.PI).toFloat()
//            )
//        )
//    }
//}

fun newCalculation() {
    for (i in 0 until Data.inputData.size) {
        Data.inputData[i].calculatedArea = 0f
    }

    for (c in Data.inputData) {
        Data.importMap.getIntersectRectanglesArea(c, Data.importMap)
    }
}
