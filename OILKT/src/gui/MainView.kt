package gui

import backend.Circle
import backend.Data
import backend.calculate
import backend.massiveTest
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

        val requestView: RequestView by inject()
        val outView: OutDataListView by inject()
        val fileInputView: FileImportView by inject()

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
                        }
                        button("Add circle") {
                            action {
                                if ((!x.text.isFloat() || !y.text.isFloat() || !r.text.isFloat())
                                    && (!x.text.isInt() || !y.text.isInt() || !r.text.isInt())
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
                                        Circle(
                                            x.text.toFloat(),
                                            y.text.toFloat(),
                                            r.text.toFloat()
                                        )
                                    )
                                    Data.inputData.add(Circle(x.text.toFloat(), y.text.toFloat(), r.text.toFloat()))
                                }
                            }
                        }
                        button("Start calculation") {
                            action {
                                if (Data.importOilMap.size == 0) {
                                    alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please select file with data!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                } else {
                                    Data.outputData.clear()
                                    calculate(Data.inputData)
                                    Data.outputData.forEach { outView.areas.add(it) }
                                }
                            }
                        }
                        button("Start auto test") {
                            action {
                                if (Data.importOilMap.size == 0) {
                                    alert(
                                        type = Alert.AlertType.ERROR,
                                        header = "Please select file with data!",
                                        actionFn = { btnType ->
                                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            }
                                        }
                                    )
                                } else {
                                    Data.outputData.clear()
                                    massiveTest()
                                    Data.outputData.forEach { outView.areas.add(it) }
                                }
                            }
                        }
                        button("Clear") {
                            action {
                                outView.areas.clear()
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
        val circles = FXCollections.observableArrayList<Circle>()

        override val root = tableview<Circle> {
            items = circles
            column("X", Circle::x)
            column("Y", Circle::y)
            column("R", Circle::r)
        }
    }
}
