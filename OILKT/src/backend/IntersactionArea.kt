package backend

import kotlin.math.sqrt

fun intersectionArea(rect: Rectangle, circleIndex: Int, i: Int, j: Int) {
    if (Data.importMap.map[i][j].used)
        return

    val circlesToCheck = checkRectangle(rect)

//    if (circlesToCheck.trueCount() == 1) {
//        calculateAreaWithOneCircle(rect, circleIndex, i, j)
//    } else {
        calculateThisShitSomehow(circlesToCheck, i, j)
//    }
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

//fun calculateAreaWithOneCircle(rect: Rectangle, circleIndex: Int, i: Int, j: Int) {
//
//    if (Data.importMap.map[i][j].used)
//        return
//
//    Data.importMap.map[i][j].used = true
//
//    var area = 0.0f
//    val resolution = 0.01f
//    var upperBound: Float
//    var lowerBound: Float
//    var leftBound = 0.0f
//    var rightBound = 0.0f
//
//    //TODO mey be refactor this bullshit
////    if (rect.bottom() < circleIndex.y && rect.right() < circleIndex.x && backend.getDistance2(
////            backend.Point(
////                rect.bottom(),
////                rect.right()
////            ), circleIndex
////        ) > circleIndex.r ||
////        rect.top() > circleIndex.y && rect.right() < circleIndex.x && backend.getDistance2(
////            backend.Point(rect.top(), rect.right()),
////            circleIndex
////        ) > circleIndex.r ||
////        rect.bottom() < circleIndex.y && rect.left() > circleIndex.x && backend.getDistance2(
////            backend.Point(
////                rect.bottom(),
////                rect.left()
////            ), circleIndex
////        ) > circleIndex.r ||
////        rect.top() > circleIndex.y && rect.left() > circleIndex.x && backend.getDistance2(
////            backend.Point(rect.top(), rect.left()),
////            circleIndex
////        ) > circleIndex.r
////    ) {
////        return 0.0f
////    }
//
//
//    //A variable storing the nearest horizontal edge of the rectangle.
//    //Determine what is nearer to the circleIndex center - the rectangle top edge or the rectangle bottom edge
//    val nearestRectangleEdge: Float =
//        if (Math.abs(Data.inputData[circleIndex].y - rect.top()) > Math.abs(Data.inputData[circleIndex].y - rect.bottom())) {
//        rect.bottom()
//    } else {
//        rect.top()
//    }
//
//    // TODO need refactor this code, because leftBound sometimes takes value gather than right that is not correct
//    if (Data.inputData[circleIndex].y >= rect.top() && Data.inputData[circleIndex].y <= rect.bottom()) {
////        Take care if the Data.inputData[circleIndex]'s center lies within the rectangle.
//        leftBound = Math.max(-Data.inputData[circleIndex].r + Data.inputData[circleIndex].x, rect.left())
//        rightBound = Math.min(Data.inputData[circleIndex].r + Data.inputData[circleIndex].x, rect.right())
//    } else if (Data.inputData[circleIndex].r >= (Math.abs(nearestRectangleEdge - Data.inputData[circleIndex].y))) {
//        //If the Data.inputData[circleIndex]'s center lies outside of the rectangle, we can choose optimal bounds.
//        leftBound = Math.max(
//            (-Math.sqrt(
//                Data.inputData[circleIndex].r * Data.inputData[circleIndex].r - Math.abs(
//                    Math.pow(
//                        (nearestRectangleEdge - Data.inputData[circleIndex].y).toDouble(),
//                        2.0
//                    )
//                )
//            )).toFloat() + Data.inputData[circleIndex].x, rect.left()
//        )
//        rightBound = Math.min(
//            (Math.sqrt(
//                Data.inputData[circleIndex].r * Data.inputData[circleIndex].r - Math.abs(
//                    Math.pow(
//                        (nearestRectangleEdge - Data.inputData[circleIndex].y).toDouble(),
//                        2.0
//                    )
//                )
//            )).toFloat() + Data.inputData[circleIndex].x, rect.right()
//        )
//    }
//
//    var i = leftBound + resolution
//
//    while (i <= rightBound) {
//        upperBound = Math.max(
//            upperRectangleFunction(rect, i - resolution / 2),
//            upperCircleFunction(Data.inputData[circleIndex], i - resolution / 2)
//        )
//        lowerBound = Math.min(
//            lowerRectangleFunction(rect, i - resolution / 2),
//            lowerCircleFunction(Data.inputData[circleIndex], i - resolution / 2)
//        )
//        area += abs(lowerBound - upperBound) * resolution
//        i += resolution
//    }
//
//    Data.inputData[circleIndex].calculatedArea += area
//}

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
//
//fun massiveTest() {
//    val n = Data.importMap.size * Data.importMap.width
//    for (i in 1..n step 1) {
//        val r = round((Math.random() * i).toFloat())
//        val x = r + round((Math.random() * i)).toFloat()
//        val y = r + round(Math.random() * i).toFloat()
//        val c = MyCircleData(x, y, r)
//
//        Data.inputData.add(MyCircleData(c))
//        Data.outputData.add(
//            AreaOutType(
//                c,
//                Data.importMap.getIntersectRectanglesArea(c, Data.importMap),
//                (Math.pow(r.toDouble(), 2.0) * Math.PI).toFloat()
//            )
//        )
//    }
//}

fun indexFloatFill(filler: Float, height: Int, width: Int): MutableList<MutableList<IndexFloat>> {
    val result: MutableList<MutableList<IndexFloat>> = mutableListOf()
//    val list: MutableList<IndexFloat> = mutableListOf()



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