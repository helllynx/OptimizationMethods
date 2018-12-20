package backend

import java.lang.Float.max
import java.util.concurrent.CompletableFuture.runAsync
import kotlin.math.min

class Data {
    companion object {
        var importMap: MyMap = MyMap()
        var inputData: ArrayList<MyCircleData> = ArrayList()
        // this is shit and i know it
        var part0: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0,0),Pair(0,0))
        var part1: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0,0),Pair(0,0))
        var part2: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0,0),Pair(0,0))
        var part3: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0,0),Pair(0,0))

    }
}

class MyCircleData(var x: Float, var y: Float, var r: Float, var growRate: Float) {
    var calculatedArea: Double = 0.0
}

class IndexFloat(var value: Double) {
    var subMap: BooleanArray = booleanArrayOf()
}

class MyMap(
    var map: ArrayList<List<IndexFloat>> = arrayListOf(),
    var mapType: MapType = MapType.OIL,
    var height: Byte = 0,
    var width: Byte = 0,
    var sizeX: Int = 0,
    var sizeY: Int = 0
) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(circleIndex: Int, startX: Int, startY: Int, endX: Int, endY: Int) {
        try {
            for (i in startY until endY) {
                for (j in startX until endX) {
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

    val circlePart0 = BooleanArray(Data.inputData.size)
    val circlePart1 = BooleanArray(Data.inputData.size)
    val circlePart2 = BooleanArray(Data.inputData.size)
    val circlePart3 = BooleanArray(Data.inputData.size)

    for (c in 0 until Data.inputData.size) {
        if (Data.inputData[c].x-Data.inputData[c].r < Data.part0.first.first*Data.importMap.width &&
            Data.inputData[c].y-Data.inputData[c].r < Data.part0.second.second*Data.importMap.height )
            circlePart0[c] = true

        if (Data.inputData[c].x-Data.inputData[c].r < Data.part1.second.first*Data.importMap.width &&
            Data.inputData[c].y+Data.inputData[c].r > Data.part1.first.second*Data.importMap.height )
            circlePart1[c] = true

        if (Data.inputData[c].x+Data.inputData[c].r > Data.part2.first.first*Data.importMap.width &&
            Data.inputData[c].y+Data.inputData[c].r > Data.part2.second.second*Data.importMap.height )
            circlePart2[c] = true

        if (Data.inputData[c].x+Data.inputData[c].r > Data.part3.second.first*Data.importMap.width &&
            Data.inputData[c].y-Data.inputData[c].r < Data.part3.first.second*Data.importMap.height )
            circlePart3[c] = true

    }
//
//    println("============================================")
//    println(circlePart0.contentToString())
//    println(circlePart1.contentToString())
//    println(circlePart2.contentToString())
//    println(circlePart3.contentToString())

    repeat(periodCount) {

    }

    runAsync {
        repeat(periodCount) {

//            riseCircleRadius()
        }
    }


    val task2 = DoMoreWorkAsync()

    await Task.WhenAll(task1, task2);

}

fun threadableCalculation(count: Int, circleIndexBool: BooleanArray, part: Pair<Pair<Int, Int>, Pair<Int, Int>>){
        for (circleIndex in 0 until Data.inputData.size) {
            if (circleIndexBool[circleIndex]) {
                val startX =
                    (max(Data.inputData[circleIndex].x - Data.inputData[circleIndex].r, 0f) / Data.importMap.width).toInt()
                val startY =
                    (max(Data.inputData[circleIndex].y - Data.inputData[circleIndex].r, 0f) / Data.importMap.height).toInt()
                val endX = min(
                    Data.importMap.sizeX,
                    ((max(
                        Data.inputData[circleIndex].x + Data.inputData[circleIndex].r,
                        0f
                    ) / Data.importMap.width).toInt() + 1)
                )
                val endY =
                    min(
                        Data.importMap.sizeY,
                        ((max(
                            Data.inputData[circleIndex].y + Data.inputData[circleIndex].r,
                            0f
                        ) / Data.importMap.height).toInt() + 1)
                    )
                Data.importMap.getIntersectRectanglesArea(circleIndex, startX, startY, endX, endY)
            }
        }
}

fun aggregateSpace(): Double {
    var space = 0.0
    for (c in Data.inputData)
        space += c.calculatedArea

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
            Data.importMap.map[row][column].subMap = booleanArrayOf()
        }
    }
}

fun checkPart0(): Boolean {

}
