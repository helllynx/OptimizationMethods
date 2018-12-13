package gui

import backend.Data
import backend.MyCircleData
import javafx.application.Application
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.Color.color
import tornadofx.View
import tornadofx.group
import kotlin.random.Random.Default.nextDouble

class MapView : View() {

    override val root = group {
        Data.inputData.add(MyCircleData(0f, 50f, 100f, 0f))
        Data.inputData.add(MyCircleData(300f, 350f, 100f, 0f))
        Data.inputData.add(MyCircleData(50f, 0f, 100f, 0f))
        Data.inputData.add(MyCircleData(50f, 1f, 120f, 0f))
        Data.inputData.add(MyCircleData(14f, 100f, 10f, 0f))
        Data.inputData.add(MyCircleData(50f, 100f, 60f, 0f))
        Data.inputData.add(MyCircleData(50f, 1f, 70f, 0f))
        Data.inputData.add(MyCircleData(400f, 600f, 350f, 0f))
        Data.inputData.add(MyCircleData(400f, 600f, 30f, 0f))
        Data.inputData.add(MyCircleData(400f, 600f, 100f, 0f))
        Data.inputData.add(MyCircleData(400f, 600f, 10f, 0f))
        Data.inputData.add(MyCircleData(400f, 600f, 220f, 0f))
        Data.inputData.add(MyCircleData(33f, 356f, 50f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 50f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 100f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 200f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 5f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 10f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 15f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 166f, 0f))
        Data.inputData.add(MyCircleData(200f, 300f, 1f, 0f))

        val canvas = Canvas(1000.0, 1000.0)
        canvas.scaleY = -1.0
        val gc = canvas.graphicsContext2D
        drawShapes(gc)
        add(canvas)
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
//        gc.fill = Color.CHARTREUSE
        gc.stroke = Color.BLUE


        for (c in Data.inputData) {
            gc.stroke = color(nextDouble(), nextDouble(), nextDouble())
            gc.strokeOval(c.x.toDouble(), c.y.toDouble(), c.r.toDouble(), c.r.toDouble())
        }

////        gc.lineWidth = 5.0
//        gc.strokeLine(40.0, 10.0, 10.0, 40.0)
//        gc.fillOval(10.0, 60.0, 30.0, 30.0)
//        gc.strokeOval(60.0, 60.0, 30.0, 60.0)
//        gc.fillRoundRect(110.0, 60.0, 30.0, 30.0, 10.0, 10.0)
//        gc.strokeRoundRect(160.0, 60.0, 30.0, 30.0, 10.0, 10.0)
//        gc.fillArc(10.0, 110.0, 30.0, 30.0, 45.0, 240.0, ArcType.OPEN)
//        gc.fillArc(60.0, 110.0, 30.0, 30.0, 45.0, 240.0, ArcType.CHORD)
//        gc.fillArc(110.0, 110.0, 30.0, 30.0, 45.0, 240.0, ArcType.ROUND)
//        gc.strokeArc(10.0, 160.0, 30.0, 30.0, 45.0, 240.0, ArcType.OPEN)
//        gc.strokeArc(60.0, 160.0, 30.0, 30.0, 45.0, 240.0, ArcType.CHORD)
//        gc.strokeArc(110.0, 160.0, 30.0, 30.0, 45.0, 240.0, ArcType.ROUND)
//        gc.fillPolygon(
//            doubleArrayOf(10.0, 40.0, 10.0, 40.0),
//            doubleArrayOf(210.0, 210.0, 240.0, 240.0), 4
//        )
//        gc.strokePolygon(
//            doubleArrayOf(60.0, 90.0, 60.0, 90.0),
//            doubleArrayOf(210.0, 210.0, 240.0, 240.0), 4
//        )
//        gc.strokePolyline(
//            doubleArrayOf(110.0, 140.0, 110.0, 140.0),
//            doubleArrayOf(210.0, 210.0, 240.0, 240.0), 4
//        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(*args)
        }
    }
}