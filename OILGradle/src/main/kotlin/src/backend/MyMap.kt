package backend

import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Data {
    companion object {
        var importMap: MyMap = MyMap()
        var importMapPORO: MyMap = MyMap()
        var inputData: ArrayList<MyCircleData> = ArrayList()
        // this is shit and i know it
        var part0: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0, 0), Pair(0, 0))
        var part1: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0, 0), Pair(0, 0))
        var part2: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0, 0), Pair(0, 0))
        var part3: Pair<Pair<Int, Int>, Pair<Int, Int>> = Pair(Pair(0, 0), Pair(0, 0))

    }
}

//class MyCircleDataTest(var x: Float, var y: Float, var r: Float, var growRate: Float) {
//    var calculatedArea: Double = 0.0
//}

class MyCircleData {
    var x: Float = 0f
    var y: Float = 0f
    var r: Float = 0f
    var growRate: Float = 0f
    var calculatedArea: Double = 0.0


    constructor(x: Float, y: Float, r: Float, growRate: Float) {
        this.x = x
        this.y = y
        this.r = r
        this.growRate = growRate
    }

    constructor(r: Float, growRate: Float) {
        this.r = r
        this.growRate = growRate
    }

    constructor(r: Float) {
        this.r = r
    }
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
        if (Data.inputData[c].x - Data.inputData[c].r < Data.part0.second.first * Data.importMap.width &&
            Data.inputData[c].y - Data.inputData[c].r < Data.part0.second.second * Data.importMap.height
        )
            circlePart0[c] = true

        if (Data.inputData[c].x - Data.inputData[c].r < Data.part1.second.first * Data.importMap.width &&
            Data.inputData[c].y + Data.inputData[c].r > Data.part1.first.second * Data.importMap.height
        )
            circlePart1[c] = true

        if (Data.inputData[c].x + Data.inputData[c].r > Data.part2.first.first * Data.importMap.width &&
            Data.inputData[c].y + Data.inputData[c].r > Data.part2.first.second * Data.importMap.height
        )
            circlePart2[c] = true

        if (Data.inputData[c].x + Data.inputData[c].r > Data.part3.first.first * Data.importMap.width &&
            Data.inputData[c].y - Data.inputData[c].r < Data.part3.second.second * Data.importMap.height
        )
            circlePart3[c] = true
    }

    val threadPool = Executors.newFixedThreadPool(4)
    repeat(periodCount) {
        val latch = CountDownLatch(4)
        threadPool.submit {
            try {
                threadableCalculation(circlePart0, Data.part0)
                latch.countDown()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }

        threadPool.submit {
            try {
                threadableCalculation(circlePart1, Data.part1)
                latch.countDown()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }

        threadPool.submit {
            try {
                threadableCalculation(circlePart2, Data.part2)
                latch.countDown()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }

        threadPool.submit {
            try {
                threadableCalculation(circlePart3, Data.part3)
                latch.countDown()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }

        latch.await()
        println("REPEAT")
        riseCircleRadius()
        fullCleanMap()
    }

    threadPool.shutdown()
}


fun threadableCalculation(circleIndexBool: BooleanArray, part: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
    for (circleIndex in 0 until Data.inputData.size) {
        if (circleIndexBool[circleIndex]) {
            var startX =
                (max(Data.inputData[circleIndex].x - Data.inputData[circleIndex].r, 0f) / Data.importMap.width).toInt()
            var startY =
                (max(Data.inputData[circleIndex].y - Data.inputData[circleIndex].r, 0f) / Data.importMap.height).toInt()
            var endX = min(
                Data.importMap.sizeX,
                ((max(
                    Data.inputData[circleIndex].x + Data.inputData[circleIndex].r,
                    0f
                ) / Data.importMap.width).toInt() + 1)
            )
            var endY =
                min(
                    Data.importMap.sizeY,
                    ((max(
                        Data.inputData[circleIndex].y + Data.inputData[circleIndex].r,
                        0f
                    ) / Data.importMap.height).toInt() + 1)
                )

            endX = min(endX, part.second.first)
            startY = max(startY, part.first.second)
            endY = min(endY, part.second.second)
            startX = max(startX, part.first.first)

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
        Data.inputData[i].r += 1.45f
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


fun optimize() {
    val tempFUCK: ArrayList<Float> = arrayListOf()

    Data.inputData.forEach { tempFUCK.add(it.r) }

    riseCircleRadius()

    var sum = 0f
    val result: MutableMap<Pair<Int, Int>, Double> = hashMapOf()

    for (c in Data.inputData)
        sum += c.r

    val avrDiameter = ((sum / Data.inputData.size) / Data.importMap.width) * 2
    val arrOfDividers = getAllPossibleGrids(Data.importMap.sizeX)

    val stepXID = abs(Arrays.binarySearch(arrOfDividers, Data.importMap.sizeX / (Data.importMap.sizeX / avrDiameter).toInt()))
    val stepYID = abs(Arrays.binarySearch(arrOfDividers, Data.importMap.sizeY / (Data.importMap.sizeY / avrDiameter).toInt()))

    val stepX = arrOfDividers[stepXID]
    val stepY = arrOfDividers[stepYID]

    println("stepX: $stepX   stepY: $stepY")

    for (i in 0 until Data.importMap.sizeY step stepY) {
        for (j in 0 until Data.importMap.sizeX step stepX) {
            result[Pair(i, j)] = calcCore(i, j, stepX, stepY)
        }
    }

    val sortedMap = ArrayList(result.toList()
        .sortedByDescending { (_, value) -> value })

    val temp: ArrayList<MyCircleData> = ArrayList()

    Data.inputData = ArrayList(Data.inputData.toList()
        .sortedWith(compareBy { it.r }).reversed()
    )

    for (i in 0 until Data.inputData.size) {
        temp.add(
            MyCircleData(
                (sortedMap[i % sortedMap.size].first.first * Data.importMap.width + (stepX * Data.importMap.width) / 2).toFloat(),
                (sortedMap[i % sortedMap.size].first.second * Data.importMap.height + (stepY * Data.importMap.height) / 2).toFloat(),
                Data.inputData[i].r,
                0f
            )
        )
    }
    Data.inputData = temp
    for (i in 0 until Data.inputData.size) {
        Data.inputData[i].r = tempFUCK[i]
    }
}

fun calcCore(startY: Int, startX: Int, stepX: Int, stepY: Int): Double {
    var area = 0.0

    val mult = Data.importMap.height * Data.importMap.width

    for (i in startY until startY + stepY) {
        for (j in startX until startX + stepX) {
            area += Data.importMap.map[i][j].value * mult
        }
    }

    return area
}


fun getAllPossibleGrids(n: Int): IntArray {
    var del: ArrayList<Int> = arrayListOf()

    for (i in 2..(n / 2)) {
        if (n % i == 0)
            del.add(i)
    }
    return del.toIntArray()
}


fun getClosestK(a: ArrayList<Int>, x: Int): Int {

    var low = 0
    var high = a.size - 1

    if (high < 0)
        throw IllegalArgumentException("The array cannot be empty")

    while (low < high) {
        val mid = (low + high) / 2
        assert(mid < high)
        val d1 = Math.abs(a[mid] - x)
        val d2 = Math.abs(a[mid + 1] - x)
        if (d2 <= d1) {
            low = mid + 1
        } else {
            high = mid
        }
    }
    return a[high]
}
