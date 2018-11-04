import java.lang.Math.random
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import kotlin.math.abs


fun main(args: Array<String>) {
    val start: Long
    val map: Map
    val input = Scanner(System.`in`)
    val kryo = Kryo()

    kryo.register(Map::class.java)
    kryo.register(Map.MapType::class.java)
    kryo.register(ArrayList::class.java)
    kryo.register(FloatArray::class.java)

    println("Use previous data? (y/n)")
    val ans = input.next()
    when (ans) {
        "y" -> {
            start = System.nanoTime()
            val inputFS = Input(FileInputStream("file.bin"))
            map = kryo.readObject(inputFS, Map::class.java)
            inputFS.close()

            massiveTest(1000, map)

            val end: Long = System.nanoTime()
            println((end - start)/Math.pow(10.0, -9.0))
        }
        "n" -> {
            start = System.nanoTime()
            map = parse("../OIL/generated_data.txt")
            val outputFS = Output(FileOutputStream("file.bin"))
            kryo.writeObject(outputFS, map)
            outputFS.close()

            massiveTest(1000, map)

            val end: Long = System.nanoTime()
            println((end - start)/Math.pow(10.0, -9.0))
        }
        else -> println("YOU STUPID, I SAID ENTER Y OR N !!!")
    }
}

// Use this if you don't use coefficients from OIL map or it all equals 1
fun massiveTest(n: Int, map: Map){
    var count = 0
    val e = 10000
    for (i in 1..n step 1) {
        val r = RoundToDecimal((random()*i).toFloat())
        val x = r+(random()*i).toFloat()
        val y = r+(random()*i).toFloat()
        val temp1 = (map.getIntersectRectanglesArea(x, y, r, map))
        val temp2 = (Math.pow(r.toDouble(), 2.0) * Math.PI).toFloat()

        if (abs(temp1 - temp2)>e) {
            count+=1
            println("=========================")
            println("i: $i")
            println("r: $r")
            println("x: $x")
            println("y: $y")
            println(temp1)
            println(temp2)
        }

    }

    println(count)
}