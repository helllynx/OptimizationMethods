package gui

import backend.Data
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import gui.MyApp.Companion.kryo
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.system.measureNanoTime

class FileImportView : View() {
    private val fileTypeFilterTXT = arrayOf(FileChooser.ExtensionFilter("Data files (*.txt)", "*.txt"))
    private val fileTypeFilterBIN = arrayOf(FileChooser.ExtensionFilter("Data files (*.bin)", "*.bin"))
    private lateinit var file: String
    private lateinit var files: List<File>

    override val root = vbox {
        spacing = 10.0
        button("Read txt") {
            action {
                files = chooseFile("Open ", fileTypeFilterTXT)
                file = if (files.isEmpty()) "" else files[0].absolutePath
                if (!file.isEmpty()) {
                    println("started")
                    measureNanoTime { Data.importOilMap = backend.parse(file) }.apply(::println)
                    val outputFS = Output(FileOutputStream(file.substring(file.lastIndexOf("/") + 1) + ".bin"))
                    kryo.writeObject(outputFS, Data.importOilMap)
                    outputFS.close()
                    println("Read txt map: $file")
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

        button("Import bin") {
            action {
                files = chooseFile("Open ", fileTypeFilterBIN)
                file = if (files.isEmpty()) "" else files[0].absolutePath
                if (!file.isEmpty()) {
                    val inputFS = Input(FileInputStream(file))
                    Data.importOilMap = kryo.readObject(inputFS, backend.OilMap::class.java)
                    inputFS.close()
                    println("Import serialized map: $file")
//                    openInternalWindow(DataInputView::class,modal = false)
//                    replaceWith<DataInputView>()
                } else {
                    alert(
                        type = Alert.AlertType.ERROR,
                        header = "Please select file! (*.bin)",
                        actionFn = { btnType ->
                            if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                            }
                        }
                    )
                }
            }
        }
    }
}
