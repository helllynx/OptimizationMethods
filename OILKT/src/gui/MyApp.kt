package gui

import backend.OilMap
import com.esotericsoftware.kryo.Kryo
import tornadofx.App

class MyApp : App(MainView::class) {

    companion object {
        val kryo = Kryo()
    }

    init {
        kryo.register(OilMap::class.java)
        kryo.register(OilMap.MapType::class.java)
        kryo.register(ArrayList::class.java)
        kryo.register(FloatArray::class.java)
    }
}