import java.nio.file.Paths
import java.nio.file.Files

fun readFile(fileName: String): ArrayList<String> {
    val data: ArrayList<String> = ArrayList()
    Files.lines(Paths.get(fileName)).use { stream ->
        stream.forEach {
            data.add(it)
        }
    }
    return data
}

fun parse(pathToFile: String): Map {
    val data: ArrayList<String> = readFile(pathToFile)
    val mapType: Map.MapType
    val arrayData: ArrayList<FloatArray> = ArrayList()


    mapType = if (data[0] == "OIL") {
        Map.MapType.OIL
    } else  {
        Map.MapType.PORO
    }

    val sizeX = data[1].split(" ")[0].toInt()
    val sizeY = data[1].split(" ")[1].toInt()
    val height = data[2].split(" ")[1].toInt()
    val width = data[2].split(" ")[1].toInt()

    var index = 0

    for (i in 0 until sizeY){
        arrayData.add(data[3].split(" ").subList(index, index+sizeX).map { it.trim().toFloat() }.toFloatArray())
        index+=sizeX
    }

    return Map(arrayData, mapType, height, width)
}
