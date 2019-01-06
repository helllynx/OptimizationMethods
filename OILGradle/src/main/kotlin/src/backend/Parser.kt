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
    val height = data[2].split(" ")[1].toByte()
    val width = data[2].split(" ")[1].toByte()

    println("started")

    val arrayData = data[3]
        .split(" ")
        .map { IndexFloat(it.toDouble()) }
        .windowed(sizeX, sizeX)
        .toCollection(ArrayList(sizeX * sizeY))
if(oilMapType == MyMap.MapType.PORO)
{
    var s = 0.0
    for (i in 0 until arrayData.size) {
        for(j in 0 until arrayData.size) {
            s += arrayData[i][j].value
        }
    }
    s = s/(arrayData.size*arrayData.size)
    println(s)
}
    Data.part0 = Pair(Pair(0, 0), Pair(sizeX / 2, sizeY / 2))
    Data.part1 = Pair(Pair(0, sizeY / 2), Pair(sizeX / 2, sizeY))
    Data.part2 = Pair(Pair(sizeX / 2, sizeY / 2), Pair(sizeX, sizeY))
    Data.part3 = Pair(Pair(sizeX / 2, 0), Pair(sizeX, sizeY / 2))



    return MyMap(arrayData, oilMapType, height, width, sizeX, sizeY)
}
