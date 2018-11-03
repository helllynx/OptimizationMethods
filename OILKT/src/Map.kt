import java.lang.Float.max

class Map(var array: ArrayList<FloatArray>, var mapType: MapType, var height: Int, var width: Int) {

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(x: Float, y : Float, r: Float, map: Map): Float {
        val startX = (max(x-r, 0f)/map.width).toInt()
        val startY = (max(y-r, 0f)/map.height).toInt()
        val endX = ((max(x+r, 0f)/map.width).toInt()+1)
        val endY = ((max(y+r, 0f)/map.height).toInt()+1)

        var area = 0f

        println("getIntersectRectangles")
        println("startX: $startX")
        println("endX: $endX")
        println("startY: $startY")
        println("endY: $endY")

        try {
            for (i in startY until endY) {
                for (j in startX until endX) {
                    area += intersectionArea(
                        Rectangle(
                            (j * map.width).toFloat(),
                            (i * map.height).toFloat(),
                            map.width.toFloat(),
                            map.height.toFloat()
                        ), Point(x, y), r
                    ) * map.array[i][j]
                }
            }
        }catch (e: Exception){
//            e.printStackTrace()
            println("EXCEPTION!!!!")
        }
        return area
    }
}