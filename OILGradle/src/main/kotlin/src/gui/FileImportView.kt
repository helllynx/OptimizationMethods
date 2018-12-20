package gui

import backend.Data
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import kotlin.system.measureNanoTime

class FileImportView : View() {
    private val fileTypeFilterTXT = arrayOf(FileChooser.ExtensionFilter("Data files (*.txt)", "*.txt"))
    //    private val fileTypeFilterBIN = arrayOf(FileChooser.ExtensionFilter("Data files (*.bin)", "*.bin"))
    private lateinit var file: String
    private lateinit var files: List<File>

    override val root = vbox {
        paddingAll = 10.0
        spacing = 10.0
        button("Open data") {
            action {
                files = chooseFile("Open ", fileTypeFilterTXT)
                file = if (files.isEmpty()) "" else files[0].absolutePath
                if (!file.isEmpty()) {
                    println("started")
                    measureNanoTime { Data.importMap = backend.parse(file) }.apply(::println)
//                    val outputFS = Output(FileOutputStream(file.substring(file.lastIndexOf("/") + 1) + ".bin"))
//                    kryo.writeObject(outputFS, Data.importMap)
//                    outputFS.close()
//                    println("Read txt map: $file")
//                    openInternalWindow(DataInputView::class,modal = false)
//                    replaceWith<DataInputView>()
                } else {
                    alert(
                        type = Alert.AlertType.ERROR,
                        header = "Please select file! (*.txt)",
                        actionFn = { btnType ->
                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                            }
                        }
                    )
                }
            }
        }
//
//        button("Import bin") {
//            action {
//                //                files = chooseFile("Open ", fileTypeFilterBIN)
////                file = if (files.isEmpty()) "" else files[0].absolutePath
////                if (!file.isEmpty()) {
////                    val inputFS = Input(FileInputStream(file))
////                    Data.importMap = kryo.readObject(inputFS, MyMap::class.java)
////                    inputFS.close()
////                    println("Import serialized map: $file")
//////                    openInternalWindow(DataInputView::class,modal = false)
//////                    replaceWith<DataInputView>()
////                } else {
////                    alert(
////                        type = Alert.AlertType.ERROR,
////                        header = "Please select file! (*.bin)",
////                        actionFn = { btnType ->
////                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
////                            }
////                        }
////                    )
////                }
//            }
//        }
    }
}
