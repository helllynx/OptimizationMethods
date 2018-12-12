package backend

import java.nio.file.Files
import java.nio.file.Paths

fun readFile(fileName: String): ArrayList<String> {
    val data: ArrayList<String> = ArrayList()
    Files.lines(Paths.get(fileName)).use { stream ->
        stream.forEach {
            data.add(it)
        }
    }
    return data
}

fun parse(pathToFile: String): MyMap {
    val data: ArrayList<String> = readFile(pathToFile)
    val oilMapType: MyMap.MapType

    oilMapType = if (data[0] == "OIL") {
        MyMap.MapType.OIL
    } else {
        MyMap.MapType.PORO
    }

    val sizeX = data[1].split(" ")[0].toInt()
    val sizeY = data[1].split(" ")[1].toInt()
    val height = data[2].split(" ")[1].toInt()
    val width = data[2].split(" ")[1].toInt()

    println("started")

    val arrayData = data[3]
        .split(" ")
        .map { IndexFloat(it.toDouble()) }
        .windowed(sizeX, sizeX)
        .toCollection(ArrayList(sizeX * sizeY))

    return MyMap(arrayData, oilMapType, height, width, sizeX)
}
