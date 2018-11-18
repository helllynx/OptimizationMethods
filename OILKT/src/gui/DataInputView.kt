//package gui
//
//import backend.MyCircleData
//import backend.Data
//import backend.MyCircleData
//import javafx.collections.FXCollections
//import javafx.scene.control.Alert
//import javafx.scene.control.ButtonBar
//import javafx.scene.control.TextField
//import tornadofx.*
//
//class DataInputView : View() {
//
//
//    override val root = borderpane {
//        left(LeftSide::class)
//    }
//
//    class LeftSide : View() {
//        private var x: TextField by singleAssign()
//        private var y: TextField by singleAssign()
//        private var r: TextField by singleAssign()
//
//        private val requestView: RequestView by inject()
//
//        override val root = hbox {
//            hbox {
//                form {
//                    spacing = 10.0
//                    fieldset("MyCircleData data") {
//                        field("x") {
//                            x = textfield()
//                        }
//                        field("y") {
//                            y = textfield()
//                        }
//                        field("r") {
//                            r = textfield()
//                        }
//                    }
//                    button("Add circle") {
//                        action {
//                            if ((!x.text.isFloat() || !y.text.isFloat() || !r.text.isFloat())
//                                && (!x.text.isInt() || !y.text.isInt() || !r.text.isInt())
//                            ) {
//                                alert(
//                                    type = Alert.AlertType.ERROR,
//                                    header = "BAD INPUT! Enter Float or Int value!",
//                                    actionFn = { btnType ->
//                                        if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
//                                        }
//                                    }
//                                )
//                            } else {
//                                requestView.circles.add(MyCircleData(x.text.toFloat(), y.text.toFloat(), r.text.toFloat()))
//                                Data.inputData.add(MyCircleData(MyCircleData(x.text.toFloat(), y.text.toFloat(), r.text.toFloat())))
//                            }
//                        }
//                    }
//                    button("Start calculation") {
//                        action {
////                            calculate(Data.inputData)
//
//                        }
//                    }
//                    button("Start auto test") {
//                        action {
////                            massiveTest()
//                        }
//                    }
//                }
//            }
//
//            add(requestView)
//
//        }
//    }
//
//    class RequestView : View() {
//        val circles = FXCollections.observableArrayList<MyCircleData>()
//
//        override val root = tableview<MyCircleData> {
//            items = circles
//            column("x", MyCircleData::x)
//            column("y", MyCircleData::y)
//            column("r", MyCircleData::r)
//        }
//    }
//}
