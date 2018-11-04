import java.lang.Float.max

class Map {
    var array: ArrayList<FloatArray>
    var mapType: MapType
    var height: Int
    var width: Int

    constructor(array: ArrayList<FloatArray>, mapType: MapType, height: Int, width: Int) {
        this.array = array
        this.mapType = mapType
        this.height = height
        this.width = width
    }
    constructor() {
        this.array = ArrayList()
        this.mapType = MapType.OIL
        this.height = 0
        this.width = 0
    }

    enum class MapType { OIL, PORO }

    fun getIntersectRectanglesArea(x: Float, y : Float, r: Float, map: Map): Float {
        val startX = (max(x-r, 0f)/map.width).toInt()
        val startY = (max(y-r, 0f)/map.height).toInt()
        val endX = ((max(x+r, 0f)/map.width).toInt()+1)
        val endY = ((max(y+r, 0f)/map.height).toInt()+1)

        var area = 0f

//        println("getIntersectRectangles")
//        println("startX: $startX | " + startX*map.width)
//        println("endX: $endX | " + endX * map.width)
//        println("startY: $startY | " + startY * map.height)
//        println("endY: $endY | " + endY * map.height)

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
                    )
//                    * map.array[i][j]
//                    println("$i $j")
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return area
    }
}