package gui

import backend.MyCircleData
import javafx.collections.FXCollections
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview


class OutDataListView : View() {
    val areas = FXCollections.observableArrayList<MyCircleData>()

    override val root = tableview(areas) {
        readonlyColumn("X", MyCircleData::x)
        readonlyColumn("Y", MyCircleData::y)
        readonlyColumn("R", MyCircleData::r)
        readonlyColumn("S", MyCircleData::calculatedArea)
        readonlyColumn("Calc S", MyCircleData::theoreticallyArea)

        prefWidth = 460.0
        prefHeight = 600.0
        columnResizePolicy = SmartResize.POLICY
    }

}