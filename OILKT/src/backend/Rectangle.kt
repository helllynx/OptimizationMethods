package backend

import kotlin.math.round

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

data class Point(var x: Float, var y: Float)
data class Circle(var x: Float, var y: Float, var r: Float)
class AreaOutType(var c: Circle, var s: Float, var calcS: Float) {
    val x = c.x
    val y = c.y
    val r = c.r
}

fun getDistance(p1: Point, c: Circle): Float {
    return Math.sqrt(Math.pow((p1.x - c.x).toDouble(), 2.0) + Math.pow((p1.y - c.y).toDouble(), 2.0)).toFloat()
}

fun upperRectangleFunction(rect: Rectangle, x: Float): Float {
    if (rect.left() > x || rect.right() < x) {
        throw Exception("The requested point lies outside of the rectangle: x: $x  left: ${rect.left()}  right: ${rect.right()}")
//        return 0f
    }
    return rect.top()
}

fun lowerRectangleFunction(rect: Rectangle, x: Float): Float {
    if (rect.left() > x || rect.right() < x) {
        throw Exception("The requested point lies outside of the rectangle: x: $x  left: ${rect.left()}  right: ${rect.right()}")
//        return 0f

    }
    return rect.bottom()
}

fun upperCircleFunction(c: Circle, x: Float): Float {
    if (round(c.x - c.r) > x || round(c.x + c.r) < x) {
//        throw Exception("The requested point lies outside of the circle: $x  c.x: ${c.x}  c.r: ${c.r}")
        return 0f
    }
    return (c.y - Math.sqrt((c.r * c.r) - Math.pow(((x - c.x).toDouble()), 2.0))).toFloat()
}

fun lowerCircleFunction(c: Circle, x: Float): Float {
    if (round(c.x - c.r) > x || round(c.x + c.r) < x) {
//        throw Exception("The requested point lies outside of the circle: $x  c.x: ${c.x}  c.r: ${c.r}")
        return 0f
    }
    return (c.y + Math.sqrt((c.r * c.r) - Math.pow(((x - c.x).toDouble()), 2.0))).toFloat()
}
