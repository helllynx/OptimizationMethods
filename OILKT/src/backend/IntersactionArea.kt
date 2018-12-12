package backend

import kotlin.math.sqrt

fun intersectionArea(rect: Rectangle, circleIndex: Int, i: Int, j: Int) {
    if (Data.importMap.map[i][j].used)
        return
    val circlesToCheck = checkRectangle(rect)
    calculateThisShitSomehow(circlesToCheck, i, j)
}

fun calculateThisShitSomehow(circles: BooleanArray, i: Int, j: Int) {
    val mY = i * Data.importMap.height
    val mX = j * Data.importMap.width
    val circleIndexes = BooleanArray(circles.size)
    val currentSpoil = Data.importMap.map[i][j].value

    if (Data.importMap.map[i][j].subMap.isEmpty()) {
        Data.importMap.map[i][j].subMap =
                indexFloatFill(Data.importMap.map[i][j].value, Data.importMap.height, Data.importMap.width)
    }

    for (i_ in 0 until Data.importMap.height) {
        for (j_ in 0 until Data.importMap.width) {
            if (Data.importMap.map[i][j].subMap[i_][j_].used) {
                continue
            }
            for (c in 0 until circles.size) {
                if (getDistance2(
                        (mX + j_).toFloat(),
                        (mY + i_).toFloat(),
                        Data.inputData[c].x,
                        Data.inputData[c].y
                    ) <= Data.inputData[c].r
                ) {
                    circleIndexes[c] = true
                    Data.importMap.map[i][j].subMap[i_][j_].used = true
                } else {
                    circleIndexes[c] = false
                }
            }

            val spoil = currentSpoil / circleIndexes.trueCount()

            for (idx in 0 until circleIndexes.size) {
                if (circleIndexes[idx]) {
                    Data.inputData[idx].calculatedArea += spoil
                }
            }
            circleIndexes.fillFalse()
        }
    }
}

fun checkRectangle(rect: Rectangle): BooleanArray {
    val circles = BooleanArray(Data.inputData.size)
    for (i in 0 until Data.inputData.size) {
        if (checkCircle(Data.inputData[i], rect)) {
            circles[i] = true
        }
    }
    return circles
}

fun checkCircle(myMyCircleData: MyCircleData, rect: Rectangle): Boolean {
    if (getDistance2(rect.left(), rect.top(), myMyCircleData.x, myMyCircleData.y) < myMyCircleData.r ||
        getDistance2(rect.right(), rect.bottom(), myMyCircleData.x, myMyCircleData.y) < myMyCircleData.r ||
        getDistance2(rect.right(), rect.top(), myMyCircleData.x, myMyCircleData.y) < myMyCircleData.r ||
        getDistance2(rect.left(), rect.bottom(), myMyCircleData.x, myMyCircleData.y) < myMyCircleData.r
    ) {
        return true
    }
    return false
}

fun getDistance2(xr: Float, yr: Float, xc: Float, yc: Float): Float {
    return sqrt(Math.pow((xr - xc).toDouble(), 2.0) + Math.pow((yr - yc).toDouble(), 2.0)).toFloat()
}

fun indexFloatFill(filler: Double, height: Int, width: Int): MutableList<MutableList<IndexFloat>> {
    val result: MutableList<MutableList<IndexFloat>> = mutableListOf()

    for (i in 0 until height) {
        val list: MutableList<IndexFloat> = mutableListOf()
        for (j in 0 until width)
            list.add(IndexFloat(filler))
        result.add(list)
    }
    return result
}

fun BooleanArray.trueCount(): Int {
    var count = 0
    for (item in this) {
        if (item)
            count += 1
    }
    return count
}

fun BooleanArray.fillFalse() {
    for (i in 0 until this.size) {
        this[i] = false
    }
}
