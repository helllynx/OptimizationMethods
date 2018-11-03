class Rectangle(var x: Float, var y: Float, var width: Float, var height: Float) {
    fun left(): Float {
        return this.x
    }

    fun right(): Float {
        return this.x + this.width
    }

    fun top(): Float {
        return this.y
    }

    fun bottom(): Float {
        return this.y + this.height
    }
}

class Point(var x: Float, var y: Float)

fun getDistance(p1: Point, p2: Point): Float {
    return Math.sqrt(Math.pow((p1.x - p2.x).toDouble(), 2.0) + Math.pow((p1.y - p2.y).toDouble(), 2.0)).toFloat()
}

fun upperRectangleFunction(rect: Rectangle, x: Float): Float {
    if (rect.left() > x || rect.right() < x) {
        throw Exception("The requested point lies outside of the rectangle")
    }
    return rect.top()
}

fun lowerRectangleFunction(rect: Rectangle, x: Float): Float {
    if (rect.left() > x || rect.right() < x) {
        throw Exception("The requested point lies outside of the rectangle")
    }
    return rect.bottom()
}

fun upperCircleFunction(m: Point, r: Float, x: Float): Float {
    if (m.x - r > x || m.x + r < x) {
        throw Exception("The requested point lies outside of the circle")
    }
    return (m.y - Math.sqrt((r * r) - Math.pow(((x - m.x).toDouble()), 2.0))).toFloat()
}

fun lowerCircleFunction(m: Point, r: Float, x: Float): Float {
    if (m.x - r > x || m.x + r < x) {
        throw Exception("The requested point lies outside of the circle")
    }
    return (m.y + Math.sqrt((r * r) - Math.pow(((x - m.x).toDouble()), 2.0))).toFloat()
}
