package gui

import backend.Data
import javafx.application.Application
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import tornadofx.View
import tornadofx.circle
import tornadofx.group
import kotlin.random.Random


class MapView : View() {

    val height = 1000.0
    val width = 1000.0

    override val root = group {

        val canvas = Canvas(width, height)
        val gc = canvas.graphicsContext2D
        drawShapes(gc)
        add(canvas)

        val scale = 0.8

        for (c in Data.inputData) {
            add(circle {
                centerX = c.x.toDouble()*scale
                centerY = c.y.toDouble()*scale
                radius = c.r.toDouble()*scale
                fill = Color.TRANSPARENT
                stroke = Color.color(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())
            })
        }
    }

//    override fun start(primaryStage: Stage) {
//        primaryStage.title = "Drawing Operations Test"
//        val root = Group()
//        val canvas = Canvas(300.0, 250.0)
//        val gc = canvas.graphicsContext2D
//        drawShapes(gc)
//        root.children.add(canvas)
//        primaryStage.scene = Scene(root)
//        primaryStage.show()
//    }

    private fun drawShapes(gc: GraphicsContext) {
        val spacing = 10.0

        val hLineCount = Math.floor((height + 1) / spacing).toInt()
        val vLineCount = Math.floor((width + 1) / spacing).toInt()

        gc.stroke = Color.BLACK
        gc.lineWidth = 0.05
        for (i in 0 until hLineCount) {
            gc.strokeLine(0.0, snap((i + 1) * spacing), width, snap((i + 1) * spacing))
        }

        gc.stroke = Color.BLACK
        for (i in 0 until vLineCount) {
            gc.strokeLine(snap((i + 1) * spacing), 0.0, snap((i + 1) * spacing), height)
        }

        gc.lineWidth = 1.0
//
//        for (c in Data.inputData) {
//            gc.stroke = Color.color(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())
//            gc.strokeOval(c.x.toDouble()-c.r.toDouble(), c.y.toDouble()-c.r.toDouble(), c.r.toDouble(), c.r.toDouble())
//        }


    }

    private fun snap(y: Double): Double {
        return y.toInt() + 0.5
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(*args)
        }
    }
}