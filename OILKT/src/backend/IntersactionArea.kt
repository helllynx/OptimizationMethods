package backend

import kotlin.math.abs

fun intersectionArea(rect: Rectangle, circle: Circle): Float {
    var area = 0.0f
    val resolution = 0.1f
    var upperBound: Float
    var lowerBound: Float
    var leftBound = 0.0f
    var rightBound = 0.0f

    //TODO mey be refactor this bullshit
//    if (rect.bottom() < circle.y && rect.right() < circle.x && backend.getDistance(backend.Point(rect.bottom(), rect.right()), circle) > r ||
//        rect.top() > circle.y && rect.right() < circle.x && backend.getDistance(backend.Point(rect.top(), rect.right()), circle) > circle.r ||
//        rect.bottom() < circle.y && rect.left() > circle.x && backend.getDistance(backend.Point(rect.bottom(), rect.left()), circle) > circle.r ||
//        rect.top() > circle.y && rect.left() > circle.x && backend.getDistance(backend.Point(rect.top(), rect.left()), circle) > circle.r
//    ) {
//        return 0.0f
//    }

    //A variable storing the nearest horizontal edge of the rectangle.
    //Determine what is nearer to the circle center - the rectangle top edge or the rectangle bottom edge
    val nearestRectangleEdge: Float = if (Math.abs(circle.y - rect.top()) > Math.abs(circle.y - rect.bottom())) {
        rect.bottom()
    } else {
        rect.top()
    }

    if (circle.y >= rect.top() && circle.y <= rect.bottom()) {
        //Take care if the circle's center lies within the rectangle.
        leftBound = Math.max(-circle.r + circle.x, rect.left())
        rightBound = Math.min(circle.r + circle.x, rect.right())
    } else if (circle.r >= (Math.abs(nearestRectangleEdge - circle.y))) {
        //If the circle's center lies outside of the rectangle, we can choose optimal bounds.
        leftBound = Math.max(
            (-Math.sqrt(
                circle.r * circle.r - Math.abs(
                    Math.pow(
                        (nearestRectangleEdge - circle.y).toDouble(),
                        2.0
                    )
                )
            )).toFloat() + circle.x, rect.left()
        )
        rightBound = Math.min(
            (Math.sqrt(
                circle.r * circle.r - Math.abs(
                    Math.pow(
                        (nearestRectangleEdge - circle.y).toDouble(),
                        2.0
                    )
                )
            )).toFloat() + circle.x, rect.right()
        )
    }

    var i = leftBound + resolution / 2

    do {
        upperBound = Math.max(
            upperRectangleFunction(rect, i - resolution / 2),
            upperCircleFunction(circle, i - resolution / 2)
        )
        lowerBound = Math.min(
            lowerRectangleFunction(rect, i - resolution / 2),
            lowerCircleFunction(circle, i - resolution / 2)
        )
        area += abs(lowerBound - upperBound) * resolution
        i += resolution
    } while (i < rightBound)

    return area
}

fun testIntersectionAreaCalculation(x: Float, y: Float, width: Float, height: Float, circle: Circle) {
    println("backend.Rectangle        : x=$x y=$y width=$width height=$height")
    println("Circle           : x=$circle.x y=${circle.y} r=${circle.r}")
    println("backend.Rectangle Area   : ${width * height}")
    println("Circle Area      : ${Math.pow(circle.r.toDouble(), 2.0) * Math.PI}")
    println(
        "Intersection Area: ${intersectionArea(
            Rectangle(x, y, width, height),
            circle
        )}"
    )
}


