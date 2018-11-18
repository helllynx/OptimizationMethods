package gui

import backend.IndexFloat
import backend.MyMap
import com.esotericsoftware.kryo.Kryo
import tornadofx.App

class MyApp : App(MainView::class) {

    companion object {
        val kryo = Kryo()
    }

    init {
        kryo.register(MyMap::class.java)
        kryo.register(IndexFloat::class.java)
        kryo.register(MyMap.MapType::class.java)
        kryo.register(ArrayList::class.java)
        kryo.register(FloatArray::class.java)
    }
}