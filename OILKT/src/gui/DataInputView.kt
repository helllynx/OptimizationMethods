package gui

import backend.Circle
import backend.OilMap
import backend.calculate
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.TextField
import tornadofx.*

class DataInputView : View() {


    override val root = borderpane {
        left(LeftSide::class)
    }

    class LeftSide : View() {
        private var x: TextField by singleAssign()
        private var y: TextField by singleAssign()
        private var r: TextField by singleAssign()

        val requestView: RequestView by inject()

        override val root = hbox {
            hbox {
                form {
                    fieldset("Circle data") {
                        field("x") {
                            x = textfield()
                        }
                        field("y") {
                            y = textfield()
                        }
                        field("r") {
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
                                requestView.circles.add(Circle(x.text.toFloat(), y.text.toFloat(), r.text.toFloat()))
                                OilMap.inputData.add(Circle(x.text.toFloat(), y.text.toFloat(), r.text.toFloat()))
                            }
                        }
                    }
                    button("Start calculation") {
                        action {
                            calculate(OilMap.inputData)
                            replaceWith<OutDataListView>()
                        }
                    }
                }
            }

            add(requestView)

        }
    }

    class RequestView : View() {
        val circles = FXCollections.observableArrayList<Circle>()

        override val root = tableview<Circle> {
            items = circles
            column("x", Circle::x)
            column("y", Circle::y)
            column("r", Circle::r)
        }


    }
}
