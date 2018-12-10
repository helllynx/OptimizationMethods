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
                                if (Data.importMap.size == 0) {
                                    alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please select file with data!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                } else if (periodCount.text.isEmpty()){
                                    alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please enter period count!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                }else{
//                                    calculate(Data.inputData)
                                    newCalculation(periodCount.text.toInt())
                                    totalSpace.text = aggregateSpace().toString()
                                    for (i in 0 until Data.inputData.size) {
                                        Data.inputData[i].theoreticallyArea =
                                                (Math.pow(Data.inputData[i].r.toDouble(), 2.0) * Math.PI).toFloat()
                                    }

                                    Data.inputData.forEach { outView.areas.add(it) }
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
