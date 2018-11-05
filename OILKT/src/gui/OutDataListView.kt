package gui

import backend.AreaOutType
import backend.Data
import tornadofx.*


class OutDataListView : View() {
    override val root = tableview(Data.outputData.observable()) {
        readonlyColumn("X", AreaOutType::x)
        readonlyColumn("Y", AreaOutType::y)
        readonlyColumn("R", AreaOutType::r)
        readonlyColumn("S", AreaOutType::s)
        readonlyColumn("Calc S", AreaOutType::calcS)

        prefWidth = 460.0
        prefHeight = 600.0
        columnResizePolicy = SmartResize.POLICY
    }

}