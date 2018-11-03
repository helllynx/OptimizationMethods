fun intersectionArea(rect: Rectangle, m: Point, r: Float): Float {
    var area = 0.0f
    val resolution = 0.01f
    var upperBound: Float
    var lowerBound: Float
    var leftBound = 0.0f
    var rightBound = 0.0f

    if (rect.bottom() < m.y && rect.right() < m.x && getDistance(Point(rect.bottom(), rect.right()), m) > r ||
        rect.top() > m.y && rect.right() < m.x && getDistance(Point(rect.top(), rect.right()), m) > r ||
        rect.bottom() < m.y && rect.left() > m.x && getDistance(Point(rect.bottom(), rect.left()), m) > r ||
        rect.top() > m.y && rect.left() > m.x && getDistance(Point(rect.top(), rect.left()), m) > r
    ) {
        return 0.0f
    }

    //A variable storing the nearest horizontal edge of the rectangle.
    //Determine what is nearer to the circle center - the rectangle top edge or the rectangle bottom edge
    val nearestRectangleEdge: Float = if (Math.abs(m.y - rect.top()) > Math.abs(m.y - rect.bottom())) {
        rect.bottom()
    } else {
        rect.top()
    }

    if (m.y >= rect.top() && m.y <= rect.bottom()) {
        //Take care if the circle's center lies within the rectangle.
        leftBound = Math.max(-r + m.x, rect.left())
        rightBound = Math.min(r + m.x, rect.right())
    } else if (r >= (Math.abs(nearestRectangleEdge - m.y))) {
        //If the circle's center lies outside of the rectangle, we can choose optimal bounds.
        leftBound = Math.max(
            (-Math.sqrt(
                r * r - Math.abs(
                    Math.pow(
                        (nearestRectangleEdge - m.y).toDouble(),
                        2.0
                    )
                )
            )).toFloat() + m.x, rect.left()
        )
        rightBound = Math.min(
            (Math.sqrt(
                r * r - Math.abs(
                    Math.pow(
                        (nearestRectangleEdge - m.y).toDouble(),
                        2.0
                    )
                )
            )).toFloat() + m.x, rect.right()
        )
    }

    var i = leftBound + resolution

    do {
        upperBound = Math.max(
            upperRectangleFunction(rect, i - resolution / 2),
            upperCircleFunction(m, r, i - resolution / 2)
        )
        lowerBound = Math.min(
            lowerRectangleFunction(rect, i - resolution / 2),
            lowerCircleFunction(m, r, i - resolution / 2)
        )
        area += (lowerBound - upperBound) * resolution
        i += resolution
    } while (i <= rightBound)

    return area
}

fun testIntersectionAreaCalculation(x: Float, y: Float, width: Float, height: Float, circleX: Float, circleY: Float, r: Float) {
    println("Rectangle        : x=$x y=$y width=$width height=$height")
    println("Circle           : x=$circleX y=$circleY r=$r")
    println("Rectangle Area   : ${width * height}")
    println("Circle Area      : ${Math.pow(r.toDouble(), 2.0) * Math.PI}")
    println("Intersection Area: ${intersectionArea(Rectangle(x, y, width, height), Point(circleX, circleY), r)}")
}


