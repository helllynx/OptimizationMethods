package gui

import backend.Data
import backend.MyCircleData
import backend.aggregateSpace
import backend.newCalculation
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.TextField
import tornadofx.*
import kotlin.random.Random
import kotlin.system.measureNanoTime

class MainView : View() {
    override val root = borderpane {
        left(LeftSide::class)
    }

    class LeftSide : View() {
        private var x: TextField by singleAssign()
        private var y: TextField by singleAssign()
        private var r: TextField by singleAssign()
        private var rate: TextField by singleAssign()
        private var periodCount: TextField by singleAssign()
        private var totalSpace: TextField by singleAssign()

        private val requestView: RequestView by inject()
        private val outView: OutDataListView by inject()
        private val fileInputView: FileImportView by inject()

        override val root = hbox {
            hbox {
                vbox {
                    add(fileInputView)
                    form {
                        spacing = 10.0
                        fieldset("Circle data") {
                            field("X") {
                                x = textfield()
                            }
                            field("Y") {
                                y = textfield()
                            }
                            field("R") {
                                r = textfield()
                            }
                            field("Rate") {
                                rate = textfield()
                            }
                        }
                        button("Add circle") {
                            action {
                                if ((!x.text.isFloat() || !y.text.isFloat() || !r.text.isFloat() || !rate.text.isFloat())
                                    && (!x.text.isInt() || !y.text.isInt() || !r.text.isInt() || !rate.text.isInt())
                                ) {
                                    alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "BAD INPUT! Enter Float or Int value!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                } else {
                                    requestView.circles.add(
                                        MyCircleData(
                                            x.text.toFloat(),
                                            y.text.toFloat(),
                                            r.text.toFloat(),
                                            rate.text.toFloat()
                                        )
                                    )
                                    Data.inputData.add(
                                        MyCircleData(
                                            x.text.toFloat(),
                                            y.text.toFloat(),
                                            r.text.toFloat(),
                                            rate.text.toFloat()
                                        )
                                    )
                                }
                            }
                        }
                        button("Start calculation") {
                            action {
                                when {
                                    Data.importMap.sizeX == 0 -> alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please select file with data!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                    periodCount.text.isEmpty() -> alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please enter period count!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                    else -> {

                                        for (i in 0..20) {
                                            Data.inputData.add(
                                                MyCircleData(
                                                    Random.nextDouble(
                                                        500.0,
                                                        (Data.importMap.sizeX * Data.importMap.width).toDouble()
                                                    ).toFloat(),
                                                    Random.nextDouble(
                                                        500.0,
                                                        (Data.importMap.sizeY * Data.importMap.height).toDouble()
                                                    ).toFloat(),
                                                    Random.nextDouble(
                                                        500.0,
                                                        5000.0
                                                    ).toFloat(),
                                                    Random.nextDouble(
                                                        50.0,
                                                        200.0
                                                    ).toFloat()
                                                )


                                            )
                                        }

//                                         KIRILL TEST DATA = 352845
//                                                                            Data.inputData.add(MyCircleData(0f, 50f, 100f, 0f))
//                                                                            Data.inputData.add(MyCircleData(300f, 350f, 100f, 0f))
//                                                                            Data.inputData.add(MyCircleData(50f, 0f, 100f, 0f))
//                                                                            Data.inputData.add(MyCircleData(50f, 1f, 120f, 0f))
//                                                                            Data.inputData.add(MyCircleData(14f, 100f, 10f, 0f))
//                                                                            Data.inputData.add(MyCircleData(50f, 100f, 60f, 0f))
//                                                                            Data.inputData.add(MyCircleData(50f, 1f, 70f, 0f))
//                                                                            Data.inputData.add(MyCircleData(400f, 600f, 350f, 0f))
//                                                                            Data.inputData.add(MyCircleData(400f, 600f, 30f, 0f))
//                                                                            Data.inputData.add(MyCircleData(400f, 600f, 100f, 0f))
//                                                                            Data.inputData.add(MyCircleData(400f, 600f, 10f, 0f))
//                                                                            Data.inputData.add(MyCircleData(400f, 600f, 220f, 0f))
//                                                                            Data.inputData.add(MyCircleData(33f, 356f, 50f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 50f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 100f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 200f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 5f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 10f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 15f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 166f, 0f))
//                                                                            Data.inputData.add(MyCircleData(200f, 300f, 1f, 0f))

                                        // Another set third
                                        //                                    Data.inputData.add(MyCircleData(50f, 40f, 20f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(50f, 60f, 20f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(30f, 50f, 20f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(50f, 80f, 10f, 0f))

                                        // Another set fourth
                                        //                                    Data.inputData.add(MyCircleData(50f, 70f, 20f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(30f, 60f, 20f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(70f, 50f, 30f, 0f))
                                        //                                    Data.inputData.add(MyCircleData(40f, 40f, 30f, 0f))

                                        // Another set data1
                                        //                                    Data.inputData.add(MyCircleData(5000f, 5000f, 100f, 50f))

                                        measureNanoTime { newCalculation(periodCount.text.toInt()) }.apply(::println)
                                        totalSpace.text = aggregateSpace().toString()

                                        Data.inputData.forEach { outView.areas.add(it) }
                                    }
                                }
                            }
                        }

                        button("Clear Out") {
                            action {
                                outView.areas.clear()
                            }
                        }
                        button("Clear input circles") {
                            action {
                                requestView.circles.clear()
                                Data.inputData.clear()
                            }
                        }
                        fieldset("Period data") {
                            field("Period count") {
                                periodCount = textfield()
                            }
                        }
                        fieldset("TOTAL") {
                            field("Total space") {
                                totalSpace = textfield()
                            }
                        }
                        fieldset("MAP parameters") {

                        }
                        label {
                            text = "X: ${Data.importMap.sizeX}  Y: ${Data.importMap.sizeY}\n" +
                                    "height: ${Data.importMap.height}\n" +
                                    "width: ${Data.importMap.width}"
                        }
                        button("TEST") {
                            action {
                                MapView().openWindow()
                            }
                        }
                    }
                }
                add(requestView)
            }
            hbox {
                add(outView)
            }
        }
    }

    class RequestView : View() {
        val circles = FXCollections.observableArrayList<MyCircleData>()!!

        override val root = tableview<MyCircleData> {
            items = circles
            column("X", MyCircleData::x)
            column("Y", MyCircleData::y)
            column("R", MyCircleData::r)
            column("Rate", MyCircleData::growRate)
        }
    }
}
